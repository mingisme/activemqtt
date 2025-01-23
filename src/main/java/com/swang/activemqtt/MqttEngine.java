package com.swang.activemqtt;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.broker.BrokerPlugin;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.security.SimpleAuthenticationPlugin;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class MqttEngine {

    private BrokerService brokerService;

    @PostConstruct
    public void init() throws Exception {
        brokerService = new BrokerService();
        brokerService.setBrokerName("my-mqtt-broker");
        brokerService.setPersistent(false);
        brokerService.setOfflineDurableSubscriberTimeout(10*60*1000);
        brokerService.setUseJmx(true);
        String bindAddress1 = "mqtt://0.0.0.0:1883";
        brokerService.addConnector(bindAddress1);
        Map<String,String> map = new HashMap<>();
        map.put("user1","password1");
        SimpleAuthenticationPlugin simple = new SimpleAuthenticationPlugin();
        simple.setUserPasswords(map);
        brokerService.setPlugins(new BrokerPlugin[]{simple});

        String bindAddress2 = "tcp://0.0.0.0:61617";
        brokerService.addConnector(bindAddress2);

        brokerService.start();

        System.out.println("mqtt broker start at " + bindAddress1);
    }
}
