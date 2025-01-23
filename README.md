# activemqtt

# run options

## app argus: 
--server.port=8080 --management.server.port=9090 --app.number=0 --app.size=2

## vm argus: 
-javaagent:/activemqtt/jmx_prometheus_javaagent-1.1.0.jar=8800:/activemqtt/activemq.yml


# visit prometheus metrics
http://localhost:8800/metrics
