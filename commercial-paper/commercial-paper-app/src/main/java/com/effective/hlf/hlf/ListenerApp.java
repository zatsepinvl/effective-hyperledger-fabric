package com.effective.hlf.hlf;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.effective.hlf.hlf.gateway.*;
import com.effective.hlf.hlf.network.DevNetwork;
import com.effective.hlf.hlf.wallet.NetworkUsers;
import com.effective.hlf.hlf.wallet.UserIdentity;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Path;

@SpringBootApplication
@Slf4j
public class ListenerApp implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ListenerApp.class);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Block {
        private long number;
    }

    private final QueueMessagingTemplate sqsTemplate;

    @Autowired
    public ListenerApp(AmazonSQSAsync amazonSQSAsync) {
        this.sqsTemplate = new QueueMessagingTemplate(amazonSQSAsync);
    }

    @Override
    public void run(String... args) {
        listenForHlfEvents();
    }

    @SqsListener("hlf-events")
    public void queueListener(Block block) {
        log.info("New block received: " + block);
    }

    private void listenForHlfEvents() {
        UserIdentity isabellaUser = NetworkUsers.getBalajiUserIdentity();
        Path networkConfigFile = DevNetwork.getNetworkConfigPath();

        TransactionEventManager eventManager = new TransactionEventManagerImpl();
        GatewayFactory factory = new GatewayFactoryImpl(eventManager, isabellaUser, networkConfigFile);
        GatewayPool gatewayPool = new GatewayPoolImpl(1, factory);
        Gateway gateway = gatewayPool.getGateway();
        Network network = gateway.getNetwork(DevNetwork.CHANNEL_NAME);

        network.addBlockListener(blockEvent -> {
            send(new Block(blockEvent.getBlockNumber()));
        });
    }

    private void send(Object payload) {
        this.sqsTemplate.convertAndSend("hlf-events", payload);
    }
}
