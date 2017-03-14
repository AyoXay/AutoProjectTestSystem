package com.flag.cu.transport.task;

import com.flag.cu.transport.enums.TopicEnum;
import com.flag.xu.neko.cleaver.task.MessageCleaverScheduledTask;
import com.flag.xu.neko.core.utils.PathUtil;
import io.netty.channel.ChannelHandlerContext;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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

    static{

    }

    public MessageSendScheduledTask(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void run() {
        final Map<String, BlockingQueue<byte[]>> queueMap = MessageCleaverScheduledTask.QUEUE_MAP;
        final String realTimeMsgId = MessageCleaverScheduledTask.realTimeMsgId;
        if (realTimeMsgId != null) {
            if (queue == null || queue.isEmpty()) {
                queue = queueMap.get(realTimeMsgId);
            }
            try {
                byte[] bytes = queue.take();
                ctx.writeAndFlush(bytes);
                Producer<String, byte[]> producer = getProducer();
                Future<RecordMetadata> future = producer.send(new ProducerRecord<>(TopicEnum.TOPIC_TE.getValue(), TopicEnum.TOPIC_TE.getValue(), bytes));
                LOG.info("send msg to kafka complete, {}", future.get().topic());
                if (future.isDone()) {
                    producer.close();
                }
            } catch (InterruptedException e) {
                LOG.error("Take msg from queue has thrown an exception, cause by {}", e.getMessage());
            } catch (ExecutionException e) {
                LOG.error(e.getMessage());
            }
        }
    }

    private Producer<String, byte[]> getProducer(){
        Properties props = new Properties();
        try {
            props.load(Files.newInputStream(PathUtil.getPath(MessageCleaverScheduledTask.class, "kafka.properties")));
        } catch (IOException e) {
            LOG.error("kafka producer start failure, {}", e.getMessage());
        }
        return new KafkaProducer<>(props);
    }
}
