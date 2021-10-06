package de.fstab.prometheus_java_client.demo;

import de.fstab.greeter.Greeter;
import de.fstab.greeter.GreetingNotFoundException;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.metrics.DoubleHistogram;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.Meter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static io.opentelemetry.api.common.AttributeKey.stringKey;
import static java.lang.Integer.parseInt;

public class GreeterServlet extends HttpServlet {

    private final DoubleHistogram latencyHistogram;
    private final LongCounter requestCount;
    private final Greeter greeter = new Greeter();

    public GreeterServlet(Meter meter) {
        requestCount = meter.counterBuilder("greeter_requests_total").build();
        latencyHistogram = meter.histogramBuilder("greeter_requests_duration_histogram_seconds").build();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long start = System.nanoTime();
        latencyHistogram.record(0.2);
        String name = request.getPathInfo().substring(1); // strip leading slash
        String status = "200";
        try {
            String greeting = greeter.sayHello(name);
            response.setStatus(parseInt(status));
            response.getWriter().print(greeting);
        } catch (GreetingNotFoundException e) {
            status = "500";
            response.setStatus(parseInt(status));
        } finally {
            double durationSeconds = (System.nanoTime() - start) * 1e-9;
            requestCount.add(1, Attributes.of(stringKey("status"), status));
            latencyHistogram.record(durationSeconds, Attributes.of(stringKey("status"), status));
        }
    }
}