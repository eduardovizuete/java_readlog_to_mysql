package com.ef.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ef.ParseLogException;
import com.ef.jpa.AccesslogRepository;
import com.ef.jpa.AccesslogfilterRepository;
import com.ef.model.AccesslogModel;
import com.ef.model.AccesslogfilterModel;
import com.ef.utils.Constants;


@Service
public class ParserService {

	@Autowired
	FileReaderService frService;
	
	@Autowired
	public AccesslogRepository accesslogRepository;
	
	@Autowired
	public AccesslogfilterRepository accesslogfilterRepository;
	
	@Autowired
	public StorageDBBatchService batchService;
	
	@Value("${register_batch:unknown}")
	public int register_batch;

	private static final Logger log = LoggerFactory.getLogger(ParserService.class);

	public void readLogFile(String fileName, Date startDate, String duration, Long threshold) throws ParseLogException {
		
		log.info("Reading file ... " + frService.fileName);
		Stream<String> fileStream = frService.readFileStreamImpl(fileName);
		
		log.info("Save logs in database ...");
		saveLogDatabaseBatch(fileStream);
		
		log.info("Search in database ...");
		List<AccesslogModel> listSearch = searchLogDatabase(startDate, duration, threshold);
		
		log.info("Save search logs in database");
		saveSearchLogDatabaseBatch(listSearch);
		
		
	}

	private void saveLogDatabaseBatch(Stream<String> fileStream) {
		DateFormat format = new SimpleDateFormat(Constants.FORMATDATEDB);
		
		List<AccesslogModel> listModel = new ArrayList<AccesslogModel>();
		
		// clean table
		//accesslogRepository.deleteAllInBatch();
		accesslogRepository.truncate();
		
		fileStream.map(line -> line.split("\\|"))
			.forEach(line -> {
						
				try {
					AccesslogModel lineLog = new AccesslogModel(
							format.parse(line[0]),
							line[1],
							line[2],
							line[3],
							line[4]);
					
					listModel.add(lineLog);	
				} catch (ParseException ex) {
					log.error(ex.getMessage());
				}
				
			});	
		
		batchService.bulkSaveAccesslog(listModel);
	}
	
	private List<AccesslogModel> searchLogDatabase(Date startDate, String duration, Long threshold) {
					
		Date finishDate = startDate;
		
		if (Constants.HOURLY.equals(duration)) {
	    	finishDate =  addHourDate(startDate);
	    } else {
	    	finishDate =  addDayDate(startDate);
	    }
		
		log.info("Searh parameters: " + startDate + "|" + finishDate + "|" + duration + "|" + threshold);
		
		List<AccesslogModel> listModel = accesslogRepository.findByRangeDateAndThresholdSQL(startDate, finishDate, threshold);
		//listModel.forEach(System.out::println);
		
		return listModel;
	}
	
	private void saveSearchLogDatabaseBatch(List<AccesslogModel> listSearch) {
		
		List<AccesslogfilterModel> listModel = new ArrayList<AccesslogfilterModel>();
		
		listSearch.forEach(line -> {
			AccesslogfilterModel alogfilter = new AccesslogfilterModel(
					line.getAcc_ip(),
					Constants.MSGQUERYREQUEST
				);
			
			listModel.add(alogfilter);
		});
		
		accesslogfilterRepository.truncate();
		batchService.bulkSaveAccesslogfilter(listModel);
		
		listModel.forEach(search -> {
			log.info(search.toString());
		});
	}

	private Date addHourDate(Date startDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		calendar.add(Calendar.HOUR, 1);
		return calendar.getTime();
	}
	
	private Date addDayDate(Date startDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		calendar.add(Calendar.DAY_OF_WEEK, 1);
		return calendar.getTime();
	}
}
