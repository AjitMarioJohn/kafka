package com.debezuim.example.source.debeziumsource.controllers;

import com.debezuim.example.source.debeziumsource.exceptions.models.ErrorDetails;
import com.debezuim.example.source.debeziumsource.services.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DebeziumControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CustomerService customerService;

    @InjectMocks
    private DebeziumController debeziumController;

    @Test
    public void testUploadEndpoint() throws Exception {
        String fileName = "test.txt";
        String contentType = "multipart/form-data";
        String fileContent = "This is a test file.";

        InputStream inputStream = new ByteArrayInputStream(fileContent.getBytes());
        MockMultipartFile multipartFile = new MockMultipartFile("file", fileName, contentType, inputStream);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/upload/SNAPSHOT")
                        .file(multipartFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andExpect(status().isOk());

    }

    @Test
    public void testUploadEndpoint_WhenFileIsNotAttached() throws Exception {
        assertThrows(IllegalArgumentException.class, () -> mockMvc.perform(MockMvcRequestBuilders.multipart("/upload/SNAPSHOT    ")
                        .file(null)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andExpect(status().is4xxClientError()) );
    }

    @Test
    public void testWelcome() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk());
    }

}