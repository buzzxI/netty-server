package icu.buzz.example;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // message will be released automatically after write
        ctx.write(msg);
        // message will be buffered until flush is called
        ctx.flush();
        // or ctx.writeAndFlush(msg);
    }
}
