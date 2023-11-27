package com.example.kafka.springkafkawikimedia.services;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface QueueService<T> {
    boolean produce(T data) throws ExecutionException, InterruptedException, TimeoutException;
}
