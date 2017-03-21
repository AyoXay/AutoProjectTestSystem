package com.flag.xu.transport.task;

import com.flag.xu.neko.cleaver.task.MessageCleaverScheduledTask;
import io.netty.channel.ChannelHandlerContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * This is a scheduled for message cleaver and send
 *
 * @author xuj
 * @version V1.0-SNAPSHOT
 * @since 2017-03-13-9:32
 */
public class MessageSendScheduledTask implements Runnable {

    private static final Logger LOG = LogManager.getLogger(MessageSendScheduledTask.class);

    private BlockingQueue<byte[]> queue = null;

    private final ChannelHandlerContext ctx;

    public MessageSendScheduledTask(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void run() {
        LOG.info("will send msg");
        final Map<String, BlockingQueue<byte[]>> queueMap = MessageCleaverScheduledTask.QUEUE_MAP;
        final String realTimeMsgId = MessageCleaverScheduledTask.realTimeMsgId;
        if (realTimeMsgId != null) {
            if (queue == null || queue.isEmpty()) {
                queue = queueMap.get(realTimeMsgId);
            }
            try {
                byte[] bytes = queue.take();
                ctx.writeAndFlush(bytes);
            } catch (InterruptedException e) {
                LOG.error("Take msg from queue has thrown an exception, cause by {}", e.getMessage());
            }
        }
    }

}
