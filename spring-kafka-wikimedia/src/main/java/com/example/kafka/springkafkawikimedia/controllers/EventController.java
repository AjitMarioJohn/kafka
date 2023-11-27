package com.example.kafka.springkafkawikimedia.controllers;

import com.example.kafka.springkafkawikimedia.services.QueueService;
import com.example.kafka.springkafkawikimedia.services.impl.KafkaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "events")
@Slf4j
public class EventController {

    private final QueueService<String> queueService;

    @PostMapping(value = "/send/{message}")
    public ResponseEntity<String> sendMessage(@PathVariable("message") String message) {
        try {
            if (queueService.produce(message)) {
                return ResponseEntity.ok("Message send successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send message.");
            }
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            log.error("EventController :: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send message.");
        }
    }

}
