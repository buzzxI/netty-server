package icu.buzz.example;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server {
    private final int port;

    public Server(int port) {
        this.port = port;
    }

    public void run() throws InterruptedException {
        // EventLoopGroup => multi selectors
        // boss handles incoming connections
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // workers handle traffic of connections (boss will register the accept connections to workers)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // helper class -> configure ServerSocketChannel
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // NioServerSocketChannel is a ServerSocketChannel with netty feature
                    // specify a handler for each SocketChannel, ChannelInitializer is used to configure SocketChannel
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // use pipeline to describe operations for SocketChannel
                            socketChannel.pipeline().addLast(new TimeServerHandler());
                        }
                    })
                    // this parameter has the same meaning as @param backlog in ServerSocket (maximum queue length for incoming connection indications)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    // .option is used to configure NioServerSocketChannel, .childOption is used to configure channel accepted by ServerChannel (NioSocketChannel)
                    // this parameter is used to send periodically packet to detect is client still alive
                    // the interval of ping-pang packet is specified by os itself (maybe not a good idea for a project)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // bind ServerSocket to the port, and fire up for incoming connections
            ChannelFuture future = bootstrap.bind(this.port).sync();

            // the server will block here until the ServerSocket is closed
            // graceful shutdown !
            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

}
