package org.example.kfp.basic.consumers;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.example.kfp.basic.commons.KafkaConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class ConsumerDemoWithGracefulShutdown {
    private static Logger LOG = LoggerFactory.getLogger(ConsumerDemoWithGracefulShutdown.class);
    public static void main(String[] args) {
        LOG.info("Triggering kafka consumer");

        // create Producer properties
        Properties properties =  new Properties();
        properties.setProperty(KafkaConstants.BOOTSTRAP_SERVERS_KEY, KafkaConstants.BOOTSTRAP_SERVER_URL);
        properties.setProperty(KafkaConstants.KEY_DESERIALIZER, StringDeserializer.class.getName());
        properties.setProperty(KafkaConstants.VALUE_DESERIALIZER, StringDeserializer.class.getName());
        properties.setProperty(KafkaConstants.GROUP_ID, KafkaConstants.GROUP_ID_VALUE);

    /*
    none :- fail if consumer-group does not exist
    earliest :- read the beginning of topic
    latest :- read the new message now
     */
        properties.setProperty(KafkaConstants.AUTO_OFFSET_RESET, "earliest");

        try (
                // create topic
                KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties)
                ) {

            final Thread mainThread = Thread.currentThread();

            // add shutdown hook
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {

                try {
                    LOG.info("Detecting a shutdown. ");
                    consumer.wakeup();

                    //join the main thread to allow execution of the code
                    mainThread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }));

            // subcribe the topic
            consumer.subscribe(List.of(KafkaConstants.TOPIC));

            // poll for data
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));

                records.forEach(record -> {
                    LOG.info("Key: {} -- value: {}", record.key(), record.value());
                    LOG.info("Partition: {} -- topic: {}", record.partition(), record.topic());
                });
            }


        } catch (WakeupException e) {
          LOG.info("Consumer is starting to shutdown");
        } catch (Exception exception) {
            LOG.error(exception.getMessage());
        }
    }
}
