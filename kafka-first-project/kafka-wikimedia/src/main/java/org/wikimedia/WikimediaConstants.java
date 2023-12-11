package org.wikimedia;

public class WikimediaConstants {
    private WikimediaConstants() {}

    public static final String BOOTSTRAP_SERVERS_KEY = "bootstrap.servers";
    public static final String BOOTSTRAP_SERVER_URL = "localhost:9092";
    public static final String KEY_SERIALIZER = "key.serializer";
    public static final String VALUE_SERIALIZER = "value.serializer";
    public static final String KEY_DESERIALIZER = "key.deserializer";
    public static final String VALUE_DESERIALIZER = "value.deserializer";
    public static final String BATCH_SIZE = "batch.size";
    public static final String TOPIC = "wikimedia.recent";
    public static final String GROUP_ID = "group.id";
    public static final String GROUP_ID_VALUE = "kafka-first-project";
    public static final String AUTO_OFFSET_RESET = "auto.offset.reset";
    public static final String PARTITION_ASSIGNMENT_STRATEGY = "partition.assignment.strategy";

    public static final String WIKIMEDIA_URL = "https://stream.wikimedia.org/v2/stream/recentchange/";

    public static final String ENABLE_IDEMPOTENCE = "enable.idempotence";
}
