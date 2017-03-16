package com.flag.xu.producer.task;

import com.flag.xu.producer.KafkaProducerProxy;
import com.flag.xu.producer.enums.TopicEnum;

/**
 * kafka producer task
 *
 * @author xuj
 * @version V1.0-SNAPSHOT
 * @since 2017-03-16-13:28
 */
public class KafkaProducerTask implements Runnable {

    @Override
    public void run() {
        KafkaProducerProxy<String, String> producerProxy = new KafkaProducerProxy<>();
        producerProxy.send2Kafka(TopicEnum.TOPIC_TE.getValue(), "hello", "world");
        producerProxy.close();
    }
}
