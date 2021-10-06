package de.fstab.prometheus_java_client.demo;

import io.opentelemetry.exporter.prometheus.PrometheusCollector;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.metrics.internal.state.MetricStorage;
import io.prometheus.client.exporter.MetricsServlet;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class DemoApplication {
    public static void main(String[] args) throws Exception {
        SdkMeterProvider meterProvider = SdkMeterProvider.builder().buildAndRegisterGlobal();
        PrometheusCollector.builder().setMetricProducer(meterProvider).buildAndRegister();

        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8085);
        server.setConnectors(new Connector[]{connector});
        ServletHandler servletHandler = new ServletHandler();
        ServletHolder servletHolder = new ServletHolder();
        servletHolder.setServlet(new GreeterServlet(meterProvider.get("OTelPrometheusDemo")));
        servletHandler.addServletWithMapping(servletHolder, "/hello/*");
        servletHandler.addServletWithMapping(MetricsServlet.class, "/metrics");
        server.setHandler(servletHandler);
        server.start();
    }
}
