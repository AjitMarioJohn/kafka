package com.debezuim.example.source.debeziumsource.exceptions.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorDetails {

    private final int statusCode;
    private final String message;

}
