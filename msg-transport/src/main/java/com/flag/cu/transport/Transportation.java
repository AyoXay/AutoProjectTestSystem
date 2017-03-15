package com.flag.cu.transport;

import com.flag.cu.transport.encoder.DataPackageEncoder;
import com.flag.cu.transport.handler.TransportHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Log4J2LoggerFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * transport socket connect class
 *
 * @author xuj
 * @version 0.1-SNAPSHOT
 * @since 2017-03-08-15:22
 */
public class Transportation {

    private static final Logger log = LogManager.getLogger(Transportation.class);

    static {
        InternalLoggerFactory.setDefaultFactory(Log4J2LoggerFactory.INSTANCE);
    }

    private String host;
    private int port;

    public Transportation(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect() {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group).channel(NioSocketChannel.class).option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline/*.addLast(new LoggingHandler(LogLevel.INFO))*/
                                .addLast(new DataPackageEncoder())
                                .addLast(new TransportHandler());
                    }
                });

        try {
            ChannelFuture future = b.connect(host, port).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("bootstrap connect failed, cause by {}", e.getMessage());
        } finally {
            group.shutdownGracefully();
        }
    }
}
