package com.ef.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ef.ParseLogException;
import com.ef.utils.Constants;

@Service
public class ArgsParserService {
	
	private static final Logger log = LoggerFactory.getLogger(ArgsParserService.class);
	
	private String accesslogParam;
	private Date startDateParam;
	private String durationParam;
	private Long thresholdParam;
	

	public void parse(String... args) throws ParseLogException {
		
		Options options = defineOptions();
		
        CommandLineParser parser = new DefaultParser();
		
        try {
            CommandLine line = parser.parse(options, args);
            
            setAccesslogParam(line.getOptionValue(Constants.ACCESSLOG));
            log.info("accesslog: " + accesslogParam);
            
            DateFormat format = new SimpleDateFormat(Constants.FORMATDATE);
            String argStartDate = line.getOptionValue(Constants.STARDATE);
            argStartDate = argStartDate.replaceAll(Constants.REGEXDOT, Constants.REGEXSPACE);
            setStartDateParam(format.parse(argStartDate));
            log.info("startDate: " + startDateParam);
            
            setDurationParam(line.getOptionValue(Constants.DURATION));
            if (!Constants.HOURLY.equals(getDurationParam()) && !Constants.DAYLY.equals(getDurationParam())) {
            	throw new ParseLogException(Constants.MSGPARAMDURATION);
            } else {
            	log.info("duration: " + durationParam);
            }
            
            setThresholdParam(new Long (line.getOptionValue(Constants.THRESHOLD)));
            log.info("threshold: " + thresholdParam);

        } catch (org.apache.commons.cli.ParseException ex) {
        	throw new ParseLogException(ex.getMessage());
        } catch (java.text.ParseException pe) {
        	throw new ParseLogException(pe.getMessage());
        } catch (NumberFormatException ne) {
        	throw new ParseLogException(ne.getMessage());
        }
	}

	private Options defineOptions() {
		
		Options options = new Options();
		Option accesslogArg = Option.builder()
				.required()
                .longOpt(Constants.ACCESSLOG)
                .hasArg()
                .valueSeparator(' ')
                .build();
		Option startDateArg = Option.builder()
				.required()
                .longOpt(Constants.STARDATE)
                .hasArg()
                .valueSeparator(' ')
                .build();
		Option durationArg = Option.builder()
				.required()
                .longOpt(Constants.DURATION)
                .hasArg()
                .valueSeparator(' ')
                .build();
		Option thresholdArg = Option.builder()
				.required()
                .longOpt(Constants.THRESHOLD)
                .hasArg()
                .valueSeparator(' ')
                .build();
		
		options.addOption(accesslogArg);
		options.addOption(startDateArg);
        options.addOption(durationArg);
        options.addOption(thresholdArg);
		return options;
	}
	
	public String getAccesslogParam() {
		return accesslogParam;
	}

	public void setAccesslogParam(String accesslogParam) {
		this.accesslogParam = accesslogParam;
	}

	public Date getStartDateParam() {
		return startDateParam;
	}

	public void setStartDateParam(Date startDateParam) {
		this.startDateParam = startDateParam;
	}

	public String getDurationParam() {
		return durationParam;
	}

	public void setDurationParam(String durationParam) {
		this.durationParam = durationParam;
	}

	public Long getThresholdParam() {
		return thresholdParam;
	}

	public void setThresholdParam(Long thresholdParam) {
		this.thresholdParam = thresholdParam;
	}

}
