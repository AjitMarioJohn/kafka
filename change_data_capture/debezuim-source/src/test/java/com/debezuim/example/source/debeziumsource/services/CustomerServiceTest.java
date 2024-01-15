package com.debezuim.example.source.debeziumsource.services;

import com.debezuim.example.source.debeziumsource.commons.OperationMode;
import com.debezuim.example.source.debeziumsource.entities.Customer;
import com.debezuim.example.source.debeziumsource.repositories.CustomerRepository;
import com.debezuim.example.source.debeziumsource.services.readers.FileReaderService;
import org.apache.tomcat.util.http.fileupload.impl.InvalidContentTypeException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CustomerServiceTest {

    @MockBean
    private CustomerRepository customerRepositoryMock;

    @MockBean
    private FileReaderService<List<Customer>> fileReaderServiceMock;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    public void testInsertCustomerFromFile_WhenArgumentIsNull() {
        assertThrows(IllegalArgumentException.class, () -> customerService.insertCustomersFromFile(OperationMode.SNAPSHOT, null));
    }

    @Test
    public void testInsertCustomerFromFile_WhenFileTypeIsNotCsvType() throws IOException {
        String fileName = "test.txt";
        String contentType = "multipart/form-data";
        String fileContent = "This is a test file.";

        InputStream inputStream = new ByteArrayInputStream(fileContent.getBytes());
        MockMultipartFile multipartFile = new MockMultipartFile("file", fileName, contentType, inputStream);

        assertThrows(InvalidContentTypeException.class, () -> customerService.insertCustomersFromFile(OperationMode.SNAPSHOT, multipartFile));

    }

//    @Test
//    public void testInsertCustomerFromFile() throws IOException {
//        String fileName = "test.csv";
//        String contentType = "multipart/form-data";
//        String fileContent = "This is a test file.";
//        InputStream inputStream = new ByteArrayInputStream(fileContent.getBytes());
//        MockMultipartFile multipartFile = new MockMultipartFile("file", fileName, contentType, inputStream);
//
//        List<Customer> customers = List.of(createMockCustomer());
//
//        when(multipartFile.getContentType()).thenReturn("text/csv");
//        when(fileReaderServiceMock.readFile(any())).thenReturn(customers);
//        when(customerRepositoryMock.saveAll(any())).thenReturn(customers);
//
//        customerService.insertCustomersFromFile(multipartFile);
//        verify(customerRepositoryMock, times(1)).saveAll(customers);
//    }

    private Customer createMockCustomer() {
        return Customer.builder().region("Quebec").country("Pakistan").name("Ignatius Vasquez").phone("1-462-343-7413").email("taciti.sociosqu.ad@hotmail.ca").address("Ap #826-8657 Vivamus St.").postal("59214").id(1L).build();
    }

}