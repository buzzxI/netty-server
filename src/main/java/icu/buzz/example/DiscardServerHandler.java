package icu.buzz.example;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

// it is the handler's responsibility to release any reference-counted object passed to the handler
// in this case -> msg
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    // this method will be called after server receive a message from client
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // discard received data
        ((ByteBuf)msg).release();
//        ReferenceCountUtil.release(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        // close channel after exception
        ctx.close();
    }
}
