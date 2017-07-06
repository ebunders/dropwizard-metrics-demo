package dw;

import com.codahale.metrics.*;

import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;
import form.Form;

import java.util.Optional;

import static com.codahale.metrics.MetricRegistry.name;
import static com.codahale.metrics.health.HealthCheck.Result.healthy;
import static com.codahale.metrics.health.HealthCheck.Result.unhealthy;
import static java.util.Optional.ofNullable;
import static java.util.concurrent.TimeUnit.*;

/**
 * Created by ernst on 4-7-17.
 */
public class MetricsSetup {
    private final MetricRegistry metrics = new MetricRegistry();
    private final HealthCheckRegistry healthCheckRegistry = new HealthCheckRegistry();
    private final Meter clicks = metrics.meter(name(MetricsSetup.class, "click.counter"));
    private final Timer responses = metrics.timer(name(MetricRegistry.class, "responses", "timer"));
    private int gaugeValue = 0;
    private String url;


    public MetricsSetup() {
        setupGauge();

        setupReporter();
        setupHealthChecks();
        measureHealthChecks();

    }

    private void measureHealthChecks() {
         healthCheckRegistry.getNames().stream()
                 .map(name -> new HealthCheckMetric(healthCheckRegistry, name))
                 .forEach(metric -> metrics.register(metric.healthCheckKey, metric));
    }

    private void setupHealthChecks() {
        healthCheckRegistry.register(
                name(MetricsSetup.class, "healthcheck.url.valid"),
                new HealthCheck() {
                    @Override
                    protected Result check() throws Exception {
                        return  ofNullable(url)
                                .map(Util::readUrl)
                                .map(r -> r
                                        ? healthy("URL [" + url + "] is ok")
                                        : unhealthy("URL [" + url + "] is DOOOOMED"))
                                .orElse(unhealthy("URL [" + url + "] has no value!"));
                    }
                });
    }

    private void setupReporter() {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
                .convertRatesTo(SECONDS)
                .convertDurationsTo(MILLISECONDS)
                .build();
        reporter. start(10, SECONDS);
    }

    public void clickMeter(){
        clicks.mark();
    }

    private void setupGauge(){
        metrics.register(name(MetricsSetup.class, "slider", "gauge"), new Gauge<Integer>() {
            public Integer getValue() {
                return gaugeValue;
            }
        });
    }

    public void setGaugeValue(int gaugeValue) {
        this.gaugeValue = gaugeValue;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void runTimed(Runnable runnable){
        final Timer.Context context = responses.time();
        runnable.run();
        context.stop();
    }
}
