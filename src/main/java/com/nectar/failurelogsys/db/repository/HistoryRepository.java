package com.nectar.failurelogsys.db.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nectar.failurelogsys.db.beans.HistoryData;
import com.nectar.failurelogsys.pojos.HistoryDataFetch;



public interface HistoryRepository extends JpaRepository<HistoryData,Long> {

	
	@Query(value = "SELECT extract(epoch from u.datetime) * 1000 as datetime, u.data as data FROM historydata as u where u.name=:name and datetime between :starttime and :endtime", nativeQuery = true)
	List<HistoryDataFetch> findHistoryData(@Param("name") String equipment,
			@Param("starttime") Timestamp starttime, @Param("endtime") Timestamp endtime);
	
	
	

}
