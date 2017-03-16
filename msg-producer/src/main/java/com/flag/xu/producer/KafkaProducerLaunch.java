package com.flag.xu.producer;

import com.flag.xu.neko.core.utils.PathUtil;
import com.flag.xu.producer.enums.TopicEnum;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;
import java.util.concurrent.Future;

/**
 * kafka producer
 *
 * @author xuj
 * @version 1.0-SNAPSHOT
 * @since 2017-03-16-11:22
 */
public class KafkaProducerLaunch {

    private static final Logger LOG = LogManager.getLogger(KafkaProducerLaunch.class);

    public void send2Kafka(String topic, String key, String msg){
        Producer<String, String> producer = getProducer();
        Future<RecordMetadata> future = producer.send(new ProducerRecord<>(topic, key, msg), (metadata, e) -> {
            if (e != null){
                LOG.error("send msg to kafka has thrown an exception, cause by {}", e.getMessage());
            } else {
                LOG.info("send msg to kafka complete, offset is {}", metadata.offset());
            }
        });
        producer.flush();
        if (future.isDone()) {
            producer.close();
        }
    }

    private Producer<String, String> getProducer(){
        Properties props = new Properties();
        try {
            props.load(Files.newInputStream(PathUtil.getPath(KafkaProducerLaunch.class, "kafka.properties")));
        } catch (IOException e) {
            LOG.error("kafka producer start failure, {}", e.getMessage());
        }
        return new KafkaProducer<>(props);
    }
}
