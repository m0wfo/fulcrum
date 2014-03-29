package com.mowforth.fulcrum.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.functions.Action1;

/**
 * CHANGEME
 */
public abstract class HttpService extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static final Logger LOG = LoggerFactory.getLogger(HttpService.class);

    /**
     * TODO document
     * @param request
     * @return
     */
    public abstract Observable<? extends HttpObject> apply(FullHttpRequest request) throws Exception;

    @Override
    protected void channelRead0(final ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        LOG.trace("Received request: {}", msg);
        apply(msg)
                .cast(HttpObject.class)
                .subscribe(new Action1<HttpObject>() {
					@Override
					public void call(HttpObject httpMessage) {
						ctx.writeAndFlush(httpMessage);
					}
				});
    }
}
