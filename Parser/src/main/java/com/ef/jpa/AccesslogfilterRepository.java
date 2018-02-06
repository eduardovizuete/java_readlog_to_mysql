package com.ef.jpa;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ef.model.AccesslogfilterModel;

public interface AccesslogfilterRepository extends JpaRepository<AccesslogfilterModel, Long> {
	
	@Query(value = "truncate table accesslogfilter", nativeQuery = true)
    @Modifying
    @Transactional
    void truncate();

}
