package com.mowforth.fulcrum.monitoring;

/**
 * CHANGEME
 */

import com.mowforth.fulcrum.core.LifecycleAware;
import com.yammer.metrics.JmxReporter;
import com.yammer.metrics.Slf4jReporter;

import javax.inject.Inject;
import javax.inject.Named;

public class Reporter implements LifecycleAware {

    private final JmxReporter reporter;
    private final Slf4jReporter slf4jReporter;
    private final long interval;

    @Inject
    public Reporter(JmxReporter reporter,
                    Slf4jReporter slf4jReporter,
                    int interval) {
        this.reporter = reporter;
        this.slf4jReporter = slf4jReporter;
        this.interval = interval;
    }

    @Override
    public void start() {
        reporter.start();
//		slf4jReporter.start(interval, TimeUnit.SECONDS);
    }

    @Override
    public void stop() {
        reporter.stop();
//		slf4jReporter.stop();
    }
}
