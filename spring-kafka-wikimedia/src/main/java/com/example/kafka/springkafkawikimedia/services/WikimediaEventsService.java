package com.example.kafka.springkafkawikimedia.services;

import com.example.kafka.springkafkawikimedia.commons.WikimediaConstant;
import com.example.kafka.springkafkawikimedia.records.WikimediaEventRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class WikimediaEventsService {
    private final WebClient webClient;
    private final QueueService<WikimediaEventRecord> queueService;

    public WikimediaEventsService(QueueService<WikimediaEventRecord> queueService) {
        this.webClient = WebClient.create(WikimediaConstant.WIKIMEDIA_URL);
        this.queueService = queueService;
    }

    public Flux<String> getEvents() {
        return webClient.get()
                .retrieve()
                .bodyToFlux(String.class);
    }

    private Mono<String> getWikimediaEvents() {
        return webClient.get()
                .retrieve()
                .bodyToMono(String.class);
    }
}
