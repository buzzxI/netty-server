package icu.buzz.example;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * TimeServer will send a 32-bit integer to client
 * server will close the connection after sending the message
 * server will not read data from client, just return current time
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    // this method will be invoked after connection established => send a 32-bit integer to client
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // future means IO operation may not be finished yet (do not close channel after writeAndFlush)
        ChannelFuture future = ctx.writeAndFlush(new UnixTime());
        // add a listener to close channel after writeAndFlush
        future.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
