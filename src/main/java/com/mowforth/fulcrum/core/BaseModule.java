package com.mowforth.fulcrum.core;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.matcher.Matchers;
import com.mowforth.fulcrum.monitoring.Trace;
import com.mowforth.fulcrum.monitoring.TraceInterceptor;
import com.yammer.metrics.JmxReporter;
import com.yammer.metrics.MetricRegistry;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * TODO
 */
class BaseModule extends AbstractModule {

    private final String serviceName;

    public BaseModule(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    protected void configure() {
        bind(EventLoopGroup.class).to(NioEventLoopGroup.class);
        bind(MetricRegistry.class).toInstance(new MetricRegistry(serviceName));
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(Trace.class), new TraceInterceptor());
    }

    @Provides
    @Singleton
    private JmxReporter provideJmxReporter(MetricRegistry registry) {
        return JmxReporter.forRegistry(registry).build();
    }
}
