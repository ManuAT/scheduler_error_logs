package com.nectar.failurelogsys.db.repository;

import java.sql.Timestamp;
import java.util.List;

import com.nectar.failurelogsys.db.model.HistoryData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryDataRepository extends JpaRepository<HistoryData,String>{

    @Query(value = "SELECT * FROM historydata u WHERE u.name=?3 AND u.datetime >= ?1 AND u.datetime < ?2", nativeQuery = true)
    List<HistoryData> selectFromHistoryData(Timestamp startDate, Timestamp endDate,String name);
    
}
