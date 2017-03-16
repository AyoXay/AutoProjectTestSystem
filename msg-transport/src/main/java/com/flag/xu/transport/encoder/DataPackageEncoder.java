package com.flag.xu.transport.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * data package encoder class
 *
 * @author xuj
 * @version 1.0-SNAPSHOT
 * @since 2017-03-08-15:40
 */
public class DataPackageEncoder extends MessageToByteEncoder<Object> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        out.writeBytes((byte[]) msg);
    }
}
