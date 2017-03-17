package com.flag.xu.producer.enums;

/**
 * kafka topic enums
 *
 * @author xuj
 * @version V1.0-SNAPSHOT
 * @since 2017-03-14-15:33
 */
public enum  TopicEnum {
    TOPIC_TE("topic_TE");

    TopicEnum(String value){
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }
}
