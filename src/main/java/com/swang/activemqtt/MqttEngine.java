package com.swang.activemqtt;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.broker.BrokerPlugin;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.network.DiscoveryNetworkConnector;
import org.apache.activemq.security.SimpleAuthenticationPlugin;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class MqttEngine {

    private BrokerService brokerService;
    private DiscoveryNetworkConnector discoveryNetworkConnector;

    public static final String user1 = "user1";
    public static final String password1 = "password1";

    @PostConstruct
    public void init() throws Exception {
        String number = System.getenv("n");
        if (number == null) {
            number = "0";
        }
        int num = Integer.parseInt(number);
        String size = System.getenv("s");
        if (size == null) {
            size = "1";
        }
        int clusterSize = Integer.parseInt(size);
        brokerService = new BrokerService();
        brokerService.setBrokerName("broker-" + num);
        brokerService.setPersistent(false);
        brokerService.setOfflineDurableSubscriberTimeout(10*60*1000);
        brokerService.setUseJmx(true);
        String bindAddress1 = "mqtt://0.0.0.0:" + (1883 + num);
        brokerService.addConnector(bindAddress1);
        Map<String,String> map = new HashMap<>();

        map.put(user1, password1);
        SimpleAuthenticationPlugin simple = new SimpleAuthenticationPlugin();
        simple.setUserPasswords(map);
        brokerService.setPlugins(new BrokerPlugin[]{simple});

        int basePort = 61617;
        ArrayList<String> list = new ArrayList<>();
        for(int i=0;i<clusterSize;i++) {
            list.add("tcp://127.0.0.1:" + (basePort + i));
        }
        String[] array = list.toArray(new String[0]);
        String join = String.join(",", array);
        discoveryNetworkConnector = new DiscoveryNetworkConnector(new URI("static:(" + join +")"));
        discoveryNetworkConnector.setName("discover-" + num);
        discoveryNetworkConnector.setUserName(user1);
        discoveryNetworkConnector.setPassword(password1);
        brokerService.addNetworkConnector(discoveryNetworkConnector);
//        brokerService.startNetworkConnector(discoveryNetworkConnector, null);

        int clusterPort = basePort + num;
        String bindAddress2 = "tcp://127.0.0.1:"+clusterPort;
        brokerService.addConnector(bindAddress2);

        brokerService.start();

        System.out.println("mqtt broker start at " + bindAddress1);
    }
}
