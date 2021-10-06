# State of the Java Metrics Libraries

Example code for my talk at the [jcon-online 2021](https://jcon.one).

Slides: [https://docs.google.com/presentation/d/1Z7qw_VxZFd5wdoHfCflTOrgzz_7PWbklnL6wl36_KQo/edit?usp=sharing](https://docs.google.com/presentation/d/1Z7qw_VxZFd5wdoHfCflTOrgzz_7PWbklnL6wl36_KQo/edit?usp=sharing)

## Preparation

* Build the project:
  ```
  mvn clean package
  ```
* Download Prometheus and Grafana
  ```
  ./download-demo.sh
  ```
* Run the Prometheus server
  ```
  cd demo/prometheus-2.30.1.linux-amd64/
  cp ../../prometheus.yml .
  ./prometheus
  ```
  The Prometheus server will run on [http://localhost:9090](http://localhost:9090).
* Run Grafana
  ```
  cd demo/grafana-8.1.5/
  ./bin/grafana-server
  ```
  Grafana will run on [http://localhost:3000](http://localhost:3000). The default login is `admin` with password `admin`. Use the Web UI to import the demo dashboards from `./dashboards/`.
* Run the client to generate traffic
  ```
  cd client
  java -jar target/client-1.0.0-SNAPSHOT.jar 8081
  java -jar target/client-1.0.0-SNAPSHOT.jar 8082
  ```

## prometheus-java-client-demo-empty

Just the servlet, no metrics.

* Run the server
  ```
  cd prometheus-java-client-demo-empty
  java -jar target/prometheus-java-client-demo-empty-1.0.0-SNAPSHOT.jar
  ```
* Example queries
  ```
  curl -i http://localhost:8080/hello/olivia
  curl -i http://localhost:8080/hello/fabian
  ```
  
## prometheus-java-client-demo-final

Metrics with [https://github.com/prometheus/client_java](https://github.com/prometheus/client_java).

* Run the server
  ```
  cd prometheus-java-client-demo-final
  java -jar target/prometheus-java-client-demo-final-1.0.0-SNAPSHOT.jar
  ```
* Example queries
  ```
  curl -i http://localhost:8081/hello/olivia
  curl -i http://localhost:8081/hello/fabian
  ```
* View the metrics
  ```
  curl http://localhost:8081/metrics
  ```
  Import `./dashboards/client_java-1633381636492.json` to Grafana to view an example dashboard.
  
## micrometer-metrics-demo

Metrics with [https://micrometer.io](https://micrometer.io).

* Run the server
  ```
  cd micrometer-metrics-demo/
  java -jar target/micrometer-metrics-demo-1.0.0-SNAPSHOT.jar
  ```
* Example queries
  ```
  curl -i http://localhost:8082/hello/olivia
  curl -i http://localhost:8082/hello/fabian
  ```
* View the metrics
  ```
  curl http://localhost:8082/actuator/prometheus
  ```
  Import `dashboards/Micrometer-1633437073435.json` to Grafana to view an example dashboard.

## dropwizard-metrics-demo

Metrics with [https://metrics.dropwizard.io](https://metrics.dropwizard.io).

* Run the server
  ```
  cd dropwizard-metrics-demo/
  java -jar target/dropwizard-metrics-demo-1.0.0-SNAPSHOT.jar server config.yml
  ```
* Example queries
  ```
  curl -i http://localhost:8083/hello/olivia
  curl -i http://localhost:8083/hello/fabian
  ```
* View the metrics
  ```
  # json
  curl http://localhost:8083/metrics
  # prometheus
  curl http://localhost:8083/prometheus-metrics
  ```
  
## microprofile-metrics-demo

Metrics with [https://microprofile.io](https://microprofile.io).

* Run the server
  ```
  cd microprofile-metrics-demo/
  ./run.sh
  ```
* Example queries
  ```
  curl -i http://localhost:8084/microprofile-metrics-demo/hello/olivia
  curl -i http://localhost:8084/microprofile-metrics-demo/hello/fabian
  ```
* View the metrics
  ```
  curl -k -u admin:admin https://localhost:8443/metrics
  ```
  
## opentelemetry-metrics-demo

Metrics with [https://opentelemetry.io](https://opentelemetry.io/).

* Run the server
  ```
  cd opentelemetry-metrics-demo/
  java -jar target/opentelemetry-metrics-demo-1.0.0-SNAPSHOT.jar
  ```
* Example queries
  ```
  curl -i http://localhost:8085/hello/olivia
  curl -i http://localhost:8085/hello/fabian
  ```
* View the metrics
  ```
  curl http://localhost:8085/metrics
  ```
