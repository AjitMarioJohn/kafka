package com.debezuim.example.source.debeziumsource.controllers;

import com.debezuim.example.source.debeziumsource.commons.OperationMode;
import com.debezuim.example.source.debeziumsource.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class DebeziumController {

    private final CustomerService customerService;

    @PostMapping(path = "/upload/{mode}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(@PathVariable(value = "mode", required = true) OperationMode mode, @RequestPart("file") MultipartFile file) throws IOException {
        customerService.insertCustomersFromFile(mode, file);
        return ResponseEntity.ok("File uploaded");
    }

    @GetMapping
    public ResponseEntity<String> welcome() {
        return ResponseEntity.ok("Welcome");
    }

}
