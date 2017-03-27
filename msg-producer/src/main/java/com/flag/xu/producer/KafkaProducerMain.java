package com.flag.xu.producer;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Future;

/**
 * kafka producer main class
 *
 * @author xuj
 * @version V1.0-SNAPSHOT
 * @since 2017-03-17-15:25
 */
public class KafkaProducerMain {

    private static final Logger LOG = LogManager.getLogger(KafkaProducerMain.class);

    private static final int TIME_OUT_MILLI = 10000;

    public static void main(String[] args) {
        LOG.info("producer start");
        try (KafkaProducerProxy<String, String> proxy = new KafkaProducerProxy<>()) {
            Future<RecordMetadata> future = proxy.send2Kafka("topic_CMD", "cmd3110", "{\"uqBusinessId\":\"CP00057\",\"agrTyp\":0,\"cltDrvTme\":\"600\",\"cltRstTme\":\"100\"}");
            long start = System.currentTimeMillis();
            while (!future.isDone()) {
                if (System.currentTimeMillis() - start > TIME_OUT_MILLI) {
                    LOG.error("send time out");
                    return;
                }
            }
            LOG.info("It's done");
        }
    }
}
