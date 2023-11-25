package org.example.kfp.basic.producers;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.example.kfp.basic.commons.KafkaConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerDemoWithCallback {
    private static Logger LOG = LoggerFactory.getLogger(ProducerDemoWithCallback.class);
    public static void main(String[] args) {
        try {
            LOG.info("Triggering kafka producer with callback");

            // create Producer properties
            Properties properties =  new Properties();
            properties.setProperty(KafkaConstants.BOOTSTRAP_SERVERS_KEY, KafkaConstants.BOOTSTRAP_SERVER_URL);
            properties.setProperty(KafkaConstants.KEY_SERIALIZER, StringSerializer.class.getName());
            properties.setProperty(KafkaConstants.VALUE_SERIALIZER, StringSerializer.class.getName());

            //Not for prod
            properties.setProperty(KafkaConstants.BATCH_SIZE, "400");

            // create the producer
            KafkaProducer<String, String> producer = new KafkaProducer<>(properties);


            // create producer record
            ProducerRecord<String, String> record = new ProducerRecord<>("first_topic", "Kafka with callback");

            // send the data
//            producer.send(record, new Callback() {
//                @Override
//                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
//                    // executes everytime when a record is successfully send or an exception is thrown
//                    if (ObjectUtils.isEmpty(e)) {
//                        LOG.info("Received new metadata \n Topic: {} \n Partitions: {}", recordMetadata.topic(), recordMetadata.partition());
//                    } else {
//                        LOG.error("Error while producing ", e);
//                    }
//                }
//            });
            producer.send(record, (recordMetadata, e) -> {
                if (ObjectUtils.isEmpty(e)) {
                    LOG.info("Received new metadata \n Topic: {} \n Partitions: {}", recordMetadata.topic(), recordMetadata.partition());
                } else {
                    LOG.error("Error while producing ", e);
                }
            });

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
