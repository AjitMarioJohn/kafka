package com.debezuim.example.source.debeziumsource.services;

import com.debezuim.example.source.debeziumsource.commons.OperationMode;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CustomerService {

    void insertCustomersFromFile(OperationMode mode, MultipartFile file) throws IOException;

}
