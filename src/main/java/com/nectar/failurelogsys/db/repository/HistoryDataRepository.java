package com.nectar.failurelogsys.db.repository;

import java.util.Collection;

import com.nectar.failurelogsys.db.model.HistoryData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryDataRepository extends JpaRepository<HistoryData,String>{

    @Query(value = "SELECT * FROM historydata u WHERE u.datetime >= ?1 AND u.datetime < ?2", nativeQuery = true)
    Collection<HistoryData> selectFromHistoryData(String startDate, String endDate);

    // SELECT * FROM historydata " +"WHERE  datetime >= '"+startDate+"' AND datetime < '"+endDate+"'"
    
}
