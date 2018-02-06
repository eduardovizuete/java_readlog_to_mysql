package com.ef.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ef.model.AccesslogModel;
import com.ef.model.AccesslogfilterModel;

@Service
@Transactional
public class StorageDBBatchService {

	@PersistenceContext
	private EntityManager entityManager;

	@Value("${hibernate.jdbc.batch_size}")
	private int batchSize;

	public <T extends AccesslogModel> Collection<T> bulkSaveAccesslog(Collection<T> entities) {
		final List<T> savedEntities = new ArrayList<T>(entities.size());
		int i = 0;
		for (T t : entities) {
			savedEntities.add(persistOrMergeAccesslog(t));
			i++;
			if (i % batchSize == 0) {
				// Flush a batch of inserts and release memory.
				entityManager.flush();
				entityManager.clear();
			}
		}
		return savedEntities;
	}

	private <T extends AccesslogModel> T persistOrMergeAccesslog(T t) {
		if (t.getAcc_id() == null) {
			entityManager.persist(t);
			return t;
		} else {
			return entityManager.merge(t);
		}
	}
	
	public <T extends AccesslogfilterModel> Collection<T> bulkSaveAccesslogfilter(Collection<T> entities) {
		final List<T> savedEntities = new ArrayList<T>(entities.size());
		int i = 0;
		for (T t : entities) {
			savedEntities.add(persistOrMergeAccesslogfilter(t));
			i++;
			if (i % batchSize == 0) {
				// Flush a batch of inserts and release memory.
				entityManager.flush();
				entityManager.clear();
			}
		}
		return savedEntities;
	}
	
	private <T extends AccesslogfilterModel> T persistOrMergeAccesslogfilter(T t) {
		if (t.getAccfil_id() == null) {
			entityManager.persist(t);
			return t;
		} else {
			return entityManager.merge(t);
		}
	}

}
