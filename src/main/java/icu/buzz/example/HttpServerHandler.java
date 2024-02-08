package icu.buzz.example;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.nio.charset.StandardCharsets;

/**
 * SimpleChannelInboundHandler is a ChannelInboundHandlerAdapter with generic type -> object are specified
 * with HttpServerCodec, the server will receive HttpObject -> FullHttpRequest, FullHttpResponse, HttpContent, LastHttpContent
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        System.out.println("channel:" + ctx.channel());
        System.out.println("pipeline:" + ctx.pipeline());
        System.out.println("channel from pipeline:" + ctx.pipeline().channel());
        System.out.println("pipeline header:" + ctx.handler());
        System.out.println("ctx type:" + ctx.getClass());
        System.out.println("client address:" + ctx.channel().remoteAddress());
        if (msg instanceof HttpRequest request) {
            System.out.println("request uri:" + request.uri());

            // set response
            ByteBuf content = Unpooled.copiedBuffer("this is server", StandardCharsets.UTF_8);
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
            ctx.writeAndFlush(response);
        }
    }
}
