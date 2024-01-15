package com.debezuim.example.source.debeziumsource.services.readers;

import java.io.IOException;
import java.io.InputStream;

public interface FileReaderService<T> {
    T readFile(InputStream inputStream) throws IOException;
}
