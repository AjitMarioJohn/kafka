package org.example.kfp.basic.producers;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.example.kfp.basic.commons.KafkaConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerDemo {
    private static Logger LOG = LoggerFactory.getLogger(ProducerDemo.class);
    public static void main(String[] args) {
        try {
            LOG.info("Triggering kafka producer");

            // create Producer properties
            Properties properties =  new Properties();
            properties.setProperty(KafkaConstants.BOOTSTRAP_SERVERS_KEY, KafkaConstants.BOOTSTRAP_SERVER_URL);
            properties.setProperty(KafkaConstants.KEY_SERIALIZER, StringSerializer.class.getName());
            properties.setProperty(KafkaConstants.VALUE_SERIALIZER, StringSerializer.class.getName());

            // create the producer
            KafkaProducer<String, String> producer = new KafkaProducer<>(properties);


            // create producer record
            ProducerRecord<String, String> record = new ProducerRecord<>(KafkaConstants.TOPIC, "Hello Kafka from code!");

            // send the data
            producer.send(record);

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
