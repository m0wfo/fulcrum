package com.mowforth.fulcrum.monitoring;

import com.mowforth.fulcrum.core.LifecycleAware;
import com.yammer.metrics.JmxReporter;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * TODO document
 */
@Singleton
public class Reporter implements LifecycleAware {

    private final JmxReporter reporter;

    @Inject
    public Reporter(JmxReporter reporter) {
        this.reporter = reporter;
    }

    @Override
    public void start() {
        reporter.start();
    }

    @Override
    public void stop() {
        reporter.stop();
    }
}
