package com.ef.jpa;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ef.model.AccesslogModel;

public interface AccesslogRepository extends JpaRepository<AccesslogModel, Long> {
	
	@Query("from AccesslogModel acclog "
			+ "where acc_date > ?1 and acc_date < ?2 "
			+ "group by acc_ip "		
			+ "having count(acc_ip) >= ?3")
	List<AccesslogModel> findByRangeDateAndThreshold(Date startDate, Date finishDate, Long threshold);
	
	@Query(value = 
			"select *" + 
			",count(acc_ip) as request " + 
			"from accesslog " +
			"where acc_date > ?1 and acc_date < ?2 " +
			"group by acc_ip " +
			"having request >= ?3",
			nativeQuery=true
			)
	List<AccesslogModel> findByRangeDateAndThresholdSQL(Date startDate, Date finishDate, Long threshold);
	
	@Query(value = "truncate table accesslog", nativeQuery = true)
    @Modifying
    @Transactional
    void truncate();

}
