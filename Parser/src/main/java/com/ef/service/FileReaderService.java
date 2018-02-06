package com.ef.service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ef.ParseLogException;

@Service
public class FileReaderService {
	
	private static final Logger log = LoggerFactory.getLogger(FileReaderService.class);
	
	@Value("${fileName:unknown}")
	public String fileName;

	public FileReaderService() {
	}
	
	public Stream<String> readFileStreamImpl(String fileName) throws ParseLogException {
		Stream<String> stream = Stream.empty();
		
		try {
			/*
			// read file from resources folder
			stream = Files.lines(Paths.get(getClass().
													getClassLoader().
													getResource(fileName).
													toURI()));
			*/
			stream = Files.lines(Paths.get(fileName));
			//stream.close();
		} catch (IOException ex) {
			throw new ParseLogException(ex.getMessage());
		//} catch (URISyntaxException e1) {
		//	e1.printStackTrace();
		}
		
		return stream;
				
	}
	
	public void readFileNioImpl(String fileName) {
		byte[] buf;
		try {
			buf = Files.readAllBytes(Paths.get(getClass().
												getClassLoader().
												getResource(fileName).
												toURI()));
			String str = new String(buf, StandardCharsets.UTF_8);
			log.info(str);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public void readFileScannerImpl(String fileName) {
		StringBuilder result = new StringBuilder("");

		// Get file from resources folder
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());

		try (Scanner scanner = new Scanner(file)) {
			
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				result.append(line).append("\n");
			}

			scanner.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}