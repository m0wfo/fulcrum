package com.mowforth.fulcrum.core;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.mowforth.fulcrum.monitoring.Reporter;
import com.mowforth.netty.util.pipelines.Pipeline;
import com.netflix.governator.guice.LifecycleInjector;
import com.netflix.governator.lifecycle.LifecycleManager;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The basic unit for describing and initializing services.
 */
public class Service {

    private static final Logger LOG = LoggerFactory.getLogger(Service.class);

    private final LifecycleManager manager;
    private final Pipeline pipeline;
    private final EventLoopGroup group;

    public static class Builder {

        private Class<? extends Pipeline> pipeline;
        private Class<? extends ChannelHandler>[] handlers;
        private Module[] modules;

        private Builder() {
            handlers = new Class[] {};
            modules = new Module[] {};
        }

        public Builder withPipeline(Class<? extends Pipeline> pipeline) {
            this.pipeline = pipeline;
            return this;
        }

        public Builder withHandlers(Class<? extends ChannelHandler>... handlers) {
            this.handlers = handlers;
            return this;
        }

        public Builder withGuiceModules(Module... modules) {
            this.modules = modules;
            return this;
        }

        public Service build() {
            Preconditions.checkArgument(pipeline != null);
            Preconditions.checkArgument(handlers != null);
            Preconditions.checkArgument(handlers.length > 0, "You must specify at least one application handler.");

            Injector injector = LifecycleInjector.builder()
                    .withModules(new BaseModule("FOO"))
                    .withAdditionalModules(modules).createInjector();

            Pipeline pipelineInstance = injector.getInstance(pipeline);
            pipelineInstance.setApplicationHandlers(handlers);
            return new Service(injector, pipelineInstance);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    private Service(Injector injector, Pipeline pipeline) {
        this.manager = injector.getInstance(LifecycleManager.class);
        this.pipeline = pipeline;
        this.group = injector.getInstance(EventLoopGroup.class);
        injector.getInstance(Reporter.class);
    }

    public ChannelFuture start(int port) {
        return start("0.0.0.0", port);
    }

    public ChannelFuture start(String host, int port) {
        LOG.debug("Starting service");

        try {
            manager.start();
        } catch (Exception e) {
            Throwables.propagate(e);
        }

        return new ServerBootstrap()
                .channel(NioServerSocketChannel.class) // TODO should this be configurable?
                .group(group)
                .childHandler(pipeline)
                .bind(host, port);
    }

    public void stop() {
        group.shutdownGracefully();
        manager.close();
    }
}
