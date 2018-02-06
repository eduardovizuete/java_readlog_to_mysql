package com.ef;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ef.service.ArgsParserService;
import com.ef.service.ParserService;
import com.ef.utils.Constants;

@SpringBootApplication
public class ParserApplication implements CommandLineRunner {
	
	private static final Logger log = LoggerFactory.getLogger(ParserApplication.class);
	
	@Autowired
	ArgsParserService argsParser;
	
	@Autowired
	ParserService parserService;

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ParserApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}
	
	@Override
	public void run(String... args) throws ParseLogException {
		
		try {
			argsParser.parse(args);
			parserService.readLogFile(argsParser.getAccesslogParam(),
					argsParser.getStartDateParam(),
					argsParser.getDurationParam(),
					argsParser.getThresholdParam());
		} catch (ParseLogException ex) {
			log.error(ex.getMessage());
			showMsgParam();
		}
		
	}
	
	private void showMsgParam() {
		log.info(Constants.MSGPARAMDAILY);
		log.info(Constants.MSGPARAMHOURLY);
	}
}
