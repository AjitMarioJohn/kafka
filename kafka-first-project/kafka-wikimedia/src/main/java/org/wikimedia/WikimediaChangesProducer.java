package org.wikimedia;

import com.launchdarkly.eventsource.ConnectStrategy;
import com.launchdarkly.eventsource.EventSource;
import com.launchdarkly.eventsource.StreamException;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.EventHandler;
import java.net.URI;
import java.util.Properties;

public class WikimediaChangesProducer {

    private static final Logger LOG = LoggerFactory.getLogger(WikimediaChangesProducer.class);

    public static void main(String[] args) {

        // create Producer properties
        Properties properties =  new Properties();
        properties.setProperty(WikimediaConstants.BOOTSTRAP_SERVERS_KEY, WikimediaConstants.BOOTSTRAP_SERVER_URL);
        properties.setProperty(WikimediaConstants.KEY_SERIALIZER, StringSerializer.class.getName());
        properties.setProperty(WikimediaConstants.VALUE_SERIALIZER, StringSerializer.class.getName());

        // create the producer
        final KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        ConnectStrategy connectStrategy = ConnectStrategy.http(URI.create(WikimediaConstants.WIKIMEDIA_URL));
        EventSource.Builder builder = new EventSource.Builder(connectStrategy);
        try (EventSource eventSource = builder.build()) {
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
