package com.debezuim.example.source.debeziumsource.services;

import com.debezuim.example.source.debeziumsource.commons.OperationMode;
import com.debezuim.example.source.debeziumsource.entities.Customer;
import com.debezuim.example.source.debeziumsource.repositories.CustomerRepository;
import com.debezuim.example.source.debeziumsource.services.readers.FileReaderService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.impl.InvalidContentTypeException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{

    private static final String FILE_TYPE = "text/csv";

    private final CustomerRepository customerRepository;
    private final FileReaderService<List<Customer>> fileReaderService;

    @Override
    public void insertCustomersFromFile(OperationMode mode, MultipartFile file) throws IOException {
        Objects.requireNonNullElseGet(file, () -> {
            throw new IllegalArgumentException("File cannot be null");
        });

        if (Objects.isNull(mode)) {
            mode = OperationMode.SNAPSHOT;
        }

        if (!FILE_TYPE.equals(file.getContentType())) {
            throw new InvalidContentTypeException("Only csv file can be read");
        }

        List<Customer> customers = fileReaderService.readFile(file.getInputStream());

        switch (mode) {
            case SNAPSHOT : {
                customerRepository.saveAll(customers);
                break;
            }
            case CONTINUOUS : {
                for (Customer customer : customers) {
                    customerRepository.save(customer);
                }
                break;
            }
        }
    }
}
