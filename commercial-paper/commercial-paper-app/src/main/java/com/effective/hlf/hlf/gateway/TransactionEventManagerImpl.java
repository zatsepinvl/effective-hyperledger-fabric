package com.effective.hlf.hlf.gateway;

import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.sdk.BlockEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class TransactionEventManagerImpl implements TransactionEventManager, Consumer<BlockEvent> {
    private final Map<String, TransactionListener> txListeners = new ConcurrentHashMap<>();
    private final Map<Network, Consumer<BlockEvent>> blockListeners = new HashMap<>();

    @Override
    public void addTransactionListener(Network network, String txId, TransactionListener listener) {
        synchronized (blockListeners) {
            txListeners.put(txId, listener);
            if (blockListeners.containsKey(network)) {
                return;
            }
            network.addBlockListener(this);
            blockListeners.put(network, this);
        }
    }

    @Override
    public void removeTransactionListener(Network network, String txId) {
        txListeners.remove(txId);
    }

    @Override
    public void accept(BlockEvent blockEvent) {
        blockEvent.getTransactionEvents().forEach(tx -> {
            if (txListeners.containsKey(tx.getTransactionID())) {
                txListeners.get(tx.getTransactionID()).onTransaction(tx);
            }
        });
    }


}
