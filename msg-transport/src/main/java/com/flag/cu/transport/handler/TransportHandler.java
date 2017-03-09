package com.flag.cu.transport.handler;

import com.flag.cu.transport.util.BytesUtil;
import com.flag.xu.neko.cleaver.amput.AbstractDiscon;
import com.flag.xu.neko.cleaver.amput.DefaultMessageDiacon;
import com.flag.xu.neko.core.utils.PathUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.util.List;

/**
 * netty handler, read msg from server
 *
 * @author xuj
 * @version 1.0-SNAPSHOT
 * @since 2017-03-08-15:42
 */
public class TransportHandler extends SimpleChannelInboundHandler<Object> {

    private static final Logger log = LogManager.getLogger(TransportHandler.class);
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("get response from server");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Path path = PathUtil.getPath("F:\\07_self\\AutoProjectTestSystem\\msg-cleaver\\src\\main\\resources\\file", "111.txt");
        AbstractDiscon<Path> discon = new DefaultMessageDiacon<>();
        List<String> res = discon.unpack0(path, 17);
        request(res, ctx);
    }

    private void request(List<String> lines, ChannelHandlerContext ctx){
        lines.stream().filter(StringUtils::isNotEmpty).forEach(s -> ctx.writeAndFlush(BytesUtil.str2Bytes(s, " ")));
    }
}
