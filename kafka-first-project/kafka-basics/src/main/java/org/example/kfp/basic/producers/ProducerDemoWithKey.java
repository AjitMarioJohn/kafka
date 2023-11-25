package org.example.kfp.basic.producers;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.example.kfp.basic.commons.KafkaConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ProducerDemoWithKey {
    private static Logger LOG = LoggerFactory.getLogger(ProducerDemoWithKey.class);
    public static void main(String[] args) {
        try {
            LOG.info("Triggering kafka producer with callback");

            // create Producer properties
            Properties properties =  new Properties();
            properties.setProperty(KafkaConstants.BOOTSTRAP_SERVERS_KEY, KafkaConstants.BOOTSTRAP_SERVER_URL);
            properties.setProperty(KafkaConstants.KEY_SERIALIZER, StringSerializer.class.getName());
            properties.setProperty(KafkaConstants.VALUE_SERIALIZER, StringSerializer.class.getName());

            // create the producer
            KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

            for (int index = 0; index < 10; index++) {
                final String key = String.format("id : %d", index);
                final String message = String.format("Hello :: Agent 00",index);
                // create producer record
                ProducerRecord<String, String> record = new ProducerRecord<>(KafkaConstants.TOPIC, key, message);

                producer.send(record, (recordMetadata, e) -> {
                    if (ObjectUtils.isEmpty(e)) {
                        LOG.info("Received new metadata \n Topic: {} \n Partitions: {}" +
                                "\n Key : {}", recordMetadata.topic(), recordMetadata.partition(),
                                key);
                    } else {
                        LOG.error("Error while producing ", e);
                    }
                });

                TimeUnit.MILLISECONDS.sleep(500);
            }
            // tell the producer to send all the data and block untill done --synchronous
            producer.flush();

            // flush and close the producer
            producer.close();

            LOG.info("Message send to broker");
        } catch (Exception exception) {
            LOG.error(exception.getMessage());
        }
    }
}
