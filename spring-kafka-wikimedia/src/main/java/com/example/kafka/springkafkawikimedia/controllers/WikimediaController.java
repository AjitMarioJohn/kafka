package com.example.kafka.springkafkawikimedia.controllers;

import com.example.kafka.springkafkawikimedia.services.WikimediaEventsService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/wikimedia")
public class WikimediaController {

    private final WikimediaEventsService wikimediaEventsService;

    @GetMapping("/events")
    public Flux<String> getEvents() {
        return wikimediaEventsService.getEvents();
    }

}
