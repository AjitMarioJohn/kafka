package com.example.kafka.springkafkawikimedia.services.impl;

import com.example.kafka.springkafkawikimedia.commons.WikimediaConstant;
import com.example.kafka.springkafkawikimedia.services.QueueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaService<T> implements QueueService<T> {

    private final KafkaTemplate<String, T> kafkaTemplate;

    public boolean produce(T data) throws ExecutionException, InterruptedException, TimeoutException {
        String key = UUID.randomUUID().toString();
        ProducerRecord<String, T> record = new ProducerRecord<>(WikimediaConstant.KAFKA_TOPIC, key, data);
        CompletableFuture<SendResult<String, T>> future = kafkaTemplate.send(record);
        CompletableFuture<Boolean> didMessageSend = future.handle((sendResult, exception) -> {
            if (ObjectUtils.isEmpty(exception)) {
                log.info("Mesasge send");
                return true;
            } else {
                log.error("Failed to send message",exception);
                return false;
            }
        });
        return didMessageSend.get(20, TimeUnit.SECONDS);
    }
}
