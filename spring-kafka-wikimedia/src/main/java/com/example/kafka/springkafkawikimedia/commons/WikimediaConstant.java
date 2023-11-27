package com.example.kafka.springkafkawikimedia.commons;


import lombok.experimental.UtilityClass;

@UtilityClass
public class WikimediaConstant {
    public static final String WIKIMEDIA_URL = "https://stream.wikimedia.org/v2/stream/recentchange";
    public static final String KAFKA_TOPIC = "wikimedia.recent";
    public static final String BOOTSTRAP_SERVERS_KEY = "bootstrap.servers";
    public static final String BOOTSTRAP_SERVER_URL = "127.0.0.1:9092";
    public static final String KEY_SERIALIZER = "key.serializer";
    public static final String VALUE_SERIALIZER = "value.serializer";
    public static final String KEY_DESERIALIZER = "key.deserializer";
    public static final String VALUE_DESERIALIZER = "value.deserializer";
    public static final String BATCH_SIZE = "batch.size";
    public static final String GROUP_ID = "group.id";
    public static final String GROUP_ID_VALUE = "wikimedia.data";
    public static final String AUTO_OFFSET_RESET = "auto.offset.reset";
    public static final String PARTITION_ASSIGNMENT_STRATEGY = "partition.assignment.strategy";

}
