package com.flag.cu.transport.task;

import io.netty.channel.ChannelHandlerContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This is a scheduled for message cleaver and send
 *
 * @author xuj
 * @version V1.0-SNAPSHOT
 * @since 2017-03-13-9:32
 */
public class MessageSendScheduledTask implements Runnable {

    private static final Logger LOG = LogManager.getLogger(MessageSendScheduledTask.class);

    private final ChannelHandlerContext ctx;

    public MessageSendScheduledTask(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void run() {

    }

}
