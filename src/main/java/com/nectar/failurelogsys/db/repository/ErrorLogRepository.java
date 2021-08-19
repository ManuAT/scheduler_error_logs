package com.nectar.failurelogsys.db.repository;

import java.sql.Timestamp;
import java.util.List;

import com.nectar.failurelogsys.db.model.ErrorLog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorLogRepository extends JpaRepository<ErrorLog,Long>{

    @Query(value = "SELECT * FROM scheduler_failure_logs u WHERE u.equipment_name in ?4 AND u.client=?3 AND u.date >= ?1 AND u.date < ?2", nativeQuery = true)
    List<ErrorLog> selectFromErrorLogData(Timestamp startDate, Timestamp endDate,String client,String[] equipmentName);

    @Query(value = "SELECT * FROM scheduler_failure_logs u WHERE u.client=?3 AND u.date >= ?1 AND u.date < ?2", nativeQuery = true)
    List<ErrorLog> selectFromErrorLogDataWithDomain(Timestamp startDate,Timestamp endDate,String client);

}
