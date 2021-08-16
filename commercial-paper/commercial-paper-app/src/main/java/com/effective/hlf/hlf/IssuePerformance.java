/*
SPDX-License-Identifier: Apache-2.0
*/

package com.effective.hlf.hlf;

import com.effective.hlf.hlf.commercialpaper.CommercialPaperContractStub;
import com.effective.hlf.hlf.gateway.*;
import com.effective.hlf.hlf.logging.LoggingUtils;
import com.effective.hlf.hlf.network.BasicNetwork;
import com.effective.hlf.hlf.wallet.NetworkUsers;
import com.effective.hlf.hlf.wallet.UserIdentity;
import org.apache.logging.log4j.Level;
import org.hyperledger.fabric.gateway.ContractException;
import org.hyperledger.fabric.gateway.Gateway;

import java.nio.file.Path;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class IssuePerformance {

    private static final String CHANNEL_NAME = "mychannel";

    public static void main(String[] args) throws InterruptedException {
        LoggingUtils.setHlfSdkGlobalLogLevel(Level.WARN);

        UserIdentity isabellaUser = NetworkUsers.getIsabellaUserIdentity();
        Path networkConfigFile = BasicNetwork.getNetworkConfigPath();

        SingleNetworkTransactionEventManager eventManager = new SingleNetworkTransactionEventManager();
        GatewayFactory factory = new GatewayFactoryImpl(eventManager, isabellaUser, networkConfigFile);
        GatewayPool gatewayPool = new GatewayPoolImpl(1, factory);
        Gateway gateway1 = gatewayPool.getGateway();
        Gateway gateway2 = gatewayPool.getGateway();

        eventManager.startListening(gateway1.getNetwork(CHANNEL_NAME));

        CommercialPaperContractStub contract1 = new CommercialPaperContractStub(CHANNEL_NAME, gateway1);
        CommercialPaperContractStub contract2 = new CommercialPaperContractStub(CHANNEL_NAME, gateway2);

        int n = 20;
        CountDownLatch latch = new CountDownLatch(1);
        ExecutorService executor = Executors.newFixedThreadPool(20);
        AtomicInteger txNumber = new AtomicInteger();
        BlockingQueue<Integer> finishedTxs = new LinkedBlockingQueue<>();
        for (int i = 0; i < n; i++) {
            String id = "" + i;
            CommercialPaperContractStub contract = i % 2 == 0 ? contract1 : contract2;
            executor.execute(() -> {
                try {
                    latch.await();
                    issue(contract, id);
                    finishedTxs.add(txNumber.incrementAndGet());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        long start = System.currentTimeMillis();
        System.out.println("Start\n=====");
        latch.countDown();
        int txs = 0;
        while (txs < n) {
            txs = finishedTxs.take();
            long end = System.currentTimeMillis();
            System.out.println("Throughput tx/s: " + txs / ((end - start + 1) / 1000 + 1) + " (" + txs + ")");
        }
        executor.shutdown();
        executor.awaitTermination(100, TimeUnit.SECONDS);
        long end = System.currentTimeMillis();
        System.out.printf("=====\nFinished %d in millis %d, (tx/s %d)", n, (end - start), n / ((end - start + 1) / 1000 + 1));
    }

    private static void issue(CommercialPaperContractStub contract, String id) throws ContractException, InterruptedException, TimeoutException {
        contract.issue("MagnetoCorp", id, "2020-05-31", "2020-11-30", "5000000");
    }

}
