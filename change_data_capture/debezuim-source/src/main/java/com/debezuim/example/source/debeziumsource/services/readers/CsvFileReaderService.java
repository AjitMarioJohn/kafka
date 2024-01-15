package com.debezuim.example.source.debeziumsource.services.readers;

import com.debezuim.example.source.debeziumsource.entities.Customer;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@Service
@Log
public class CsvFileReaderService implements FileReaderService<List<Customer>>{

    @Override
    public List<Customer> readFile(InputStream inputStream) throws IOException {
        List<Customer> customers;
        try (
                Reader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            ) {
            CsvToBean<Customer> csvToBean = new CsvToBeanBuilder<Customer>(reader)
                    .withType(Customer.class)
                    .build();

            customers = csvToBean.parse();

        }
        return customers;
    }
}
