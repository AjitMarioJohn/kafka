package org.wikimedia;

import com.launchdarkly.eventsource.ConnectStrategy;
import com.launchdarkly.eventsource.EventSource;
import com.launchdarkly.eventsource.StreamException;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.Properties;

public class WikimediaChangesProducerWithHighThroughput {

    private static final Logger LOG = LoggerFactory.getLogger(WikimediaChangesProducerWithHighThroughput.class);

    public static void main(String[] args) {

        // create Producer properties
        Properties properties =  new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, WikimediaConstants.BOOTSTRAP_SERVER_URL);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // setting idempotent producer - set to true by default in versions greater than 2.8
//        properties.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");

        //High throughput producer configs
        properties.setProperty(ProducerConfig.LINGER_MS_CONFIG, Integer.toString(20));
        properties.setProperty(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
        properties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, Integer.toString(32 * 1024));

        ConnectStrategy connectStrategy = ConnectStrategy.http(URI.create(WikimediaConstants.WIKIMEDIA_URL));
        EventSource.Builder builder = new EventSource.Builder(connectStrategy);
        try (
                final KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
                EventSource eventSource = builder.build()
        ) {
            eventSource.start();
            eventSource.messages().forEach(msg  -> {
                producer.send(new ProducerRecord<>(WikimediaConstants.TOPIC,msg.getData()));
                LOG.info("Data send: {}", msg.getData());
            });
        } catch (StreamException e) {
            LOG.error("Error :; ", e);
        }

    }
}
