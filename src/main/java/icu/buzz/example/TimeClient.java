package icu.buzz.example;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;


public class TimeClient {
    public static void main(String[] args) throws InterruptedException {
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        // client only need one thread group
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            // client need to be configured by Bootstrap (server is configured by ServerBootstrap)
            // Bootstrap is used for non-server channel => client side mostly
            Bootstrap bootstrap = new Bootstrap();
            // bootstrap with only one EventLoopGroup => this group will be used as both boss and worker
            bootstrap.group(worker)
                    // NioSocketChannel is used as SocketChannel at client side
                    .channel(NioSocketChannel.class)
                    // client-side SocketChannel does not have a parent
                    .option(ChannelOption.SO_KEEPALIVE, true);
            // configure the handler for SocketChannel
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    // use pipeline to describe operations for SocketChannel
                    ch.pipeline().addLast(new TimeClientHandler());
                }
            });

            // use connect() on client side (bind on server side)
            ChannelFuture future = bootstrap.connect(host, port).sync();
            // wait until the connection is closed
            future.channel().closeFuture().sync();
        } finally {
            worker.shutdownGracefully();
        }
    }
}
