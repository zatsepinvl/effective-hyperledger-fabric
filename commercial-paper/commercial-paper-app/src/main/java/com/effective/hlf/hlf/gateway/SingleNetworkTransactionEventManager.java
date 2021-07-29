package com.effective.hlf.hlf.gateway;

import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.sdk.BlockEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;


public class SingleNetworkTransactionEventManager implements TransactionEventManager, Consumer<BlockEvent> {

    private final Map<String, TransactionListener> txListeners;
    private Network network;

    public SingleNetworkTransactionEventManager() {
        this.txListeners = new ConcurrentHashMap<>();
    }

    public void startListening(Network network) {
        this.network = network;
        network.addBlockListener(this);
    }

    @Override
    public void addTransactionListener(Network network, String txId, TransactionListener listener) {
        if (this.network == null) {
            throw new IllegalStateException("Start listening for events first.");
        }
      /*  if (!this.network.equals(network)) {
            throw new IllegalStateException("This TransactionEventManager listens for another network.");
        }*/
        txListeners.put(txId, listener);
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
