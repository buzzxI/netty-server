package icu.buzz.example;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * a class used to deal with fragmentation issue
 * ByteToMessageDecoder is a subclass of ChannelInboundHandlerAdapter
 */
public class TimeDecoder extends ByteToMessageDecoder {
    // everytime a new data is received, ByteToMessageDecoder will call decode
    // date will be cumulated into @param: in
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // just return for short counts
        if (in.readableBytes() < 4) return;

        // in will discard the read part of cumulated bytes
        out.add(new UnixTime(in.readUnsignedInt()));
    }
}
