package com.ef.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ef.ParseLogException;

@Service
public class FileReaderService {
	
	@Value("${fileName:unknown}")
	public String fileName;

	public FileReaderService() {
	}
	
	public Stream<String> readFileStreamImpl(String fileName) throws ParseLogException {
		Stream<String> stream = Stream.empty();
		
		try {
			stream = Files.lines(Paths.get(fileName));
		} catch (IOException ex) {
			throw new ParseLogException(ex.getMessage());
		}
		
		return stream;
				
	}	
	
}