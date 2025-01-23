# activemqtt

# run options
app argus: --server.port=8080 --management.server.port=9090 --app.number=0 --app.size=2
properties: -javaagent:/Users/ming.wang4/RD/github/activemqtt/jmx_prometheus_javaagent-1.1.0.jar=8800:/Users/ming.wang4/RD/github/activemqtt/activemq.yml

#visit prometheus metrics
http://localhost:8800/metrics