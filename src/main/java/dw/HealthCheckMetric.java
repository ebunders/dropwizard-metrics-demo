package dw;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;

/**
 * Created by rickt on 24-Jun-16.
 */
class HealthCheckMetric implements Gauge<Boolean> {

    private HealthCheckRegistry healthCheckRegistry;

    final String healthCheckKey;

    HealthCheckMetric(HealthCheckRegistry healthCheckRegistry, String healthCheckKey) {
        this.healthCheckRegistry = healthCheckRegistry;
        this.healthCheckKey = healthCheckKey;
    }

    @Override
    public Boolean getValue() {
        final HealthCheck.Result result = healthCheckRegistry.runHealthCheck(healthCheckKey);
        return result.isHealthy();
    }
}
