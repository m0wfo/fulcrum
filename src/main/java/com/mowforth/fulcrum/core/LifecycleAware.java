package com.mowforth.fulcrum.core;

import com.netflix.governator.annotations.WarmUp;

import javax.annotation.PreDestroy;

/**
 * Common interface for managed objects.
 *
 * <p>Implementations inherit the warmup / predestroy annotations
 * which are called when under management by Guice / Governator.</p>
 */
public interface LifecycleAware {

    /**
     * Start this service.
     *
     * <p>Open sockets, create threads or other side-effect-ridden
     * behaviour necessary for startup here.</p>
     *
     * @throws Exception
     */
    @WarmUp
    public void start() throws Exception;

    /**
     * Gracefully shut down this service.
     *
     * <p>Called prior to JVM termination.</p>
     */
    @PreDestroy
    public void stop();
}