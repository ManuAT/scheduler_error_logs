package com.nectar.failurelogsys.db.repository;

import java.util.Collection;

import com.nectar.failurelogsys.db.model.AggregationData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface AggregationDataRepository extends JpaRepository<AggregationData,Long>{
	// String sql = "SELECT * FROM aggregationdata " +"WHERE  datetime = '"+startDate+"' AND name = '"+equipmentName+"'";

    @Query(value = "SELECT * FROM historydata u WHERE u.datetime = ?1 AND u.name = ?2", nativeQuery = true)
    Collection<AggregationData> selectFromAggregationData(String startDate, String equipmentName);




    
}
