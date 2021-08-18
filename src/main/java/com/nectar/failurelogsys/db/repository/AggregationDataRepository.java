package com.nectar.failurelogsys.db.repository;

import java.sql.Timestamp;

import com.nectar.failurelogsys.db.model.AggregationData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface AggregationDataRepository extends JpaRepository<AggregationData,Long>{

    @Query(value = "SELECT * FROM historydata u WHERE u.datetime = ?1 AND u.name = ?2", nativeQuery = true)
    AggregationData selectFromAggregationData(Timestamp startDate, String equipmentName);
    
}
