package com.mowforth.fulcrum.examples;

import com.mowforth.fulcrum.core.Service;
import com.mowforth.fulcrum.http.HttpService;
import com.mowforth.netty.util.pipelines.HttpPipeline;
import io.netty.handler.codec.http.*;
import rx.Observable;

/**
 * Simple Hello World HTTP service.
 */
public class HttpHello {

    public static void main(String[] args) {
        HttpService service = new HttpService() {
            @Override
            public Observable<? extends HttpObject> apply(FullHttpRequest request) {
                return Observable.from(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK));
            }
        };

        Service.builder()
                .withPipeline(HttpPipeline.class)
                .withHandlers(service.getClass())
                .build()
                .start(8080);
    }
}
