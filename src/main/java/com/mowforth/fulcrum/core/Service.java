package com.mowforth.fulcrum.core;

import com.google.inject.Injector;
import com.netflix.governator.guice.LifecycleInjector;
import com.netflix.governator.lifecycle.LifecycleManager;

/**
 * CHANGEME
 */
public class Service {

    private final LifecycleManager manager;

    public static class Builder {

    }

    private Service(Injector injector) {
        manager = injector.getInstance(LifecycleManager.class);
    }

    public void start() {

    }
}
