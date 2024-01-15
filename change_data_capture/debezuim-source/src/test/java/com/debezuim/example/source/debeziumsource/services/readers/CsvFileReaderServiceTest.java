package com.debezuim.example.source.debeziumsource.services.readers;

import com.debezuim.example.source.debeziumsource.entities.Customer;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CsvFileReaderServiceTest {

    private static final String FILE_PATH = "src/test/resources/";
    private static final String FILE_NAME = "customer.csv";

    @InjectMocks
    private CsvFileReaderService csvFileReaderService;

    @Test
    public void testReadFile() throws IOException {
        InputStream inputStream = getFileFromResourcesAsInputStream();
        List<Customer> customers =csvFileReaderService.readFile(inputStream);

        assertNotNull(customers);
        assertEquals(1, customers.size());
    }

    public InputStream getFileFromResourcesAsInputStream() throws IOException {
        Path path = Paths.get(String.format("%s%s", FILE_PATH, FILE_NAME));
        return Files.newInputStream(path);
    }

}