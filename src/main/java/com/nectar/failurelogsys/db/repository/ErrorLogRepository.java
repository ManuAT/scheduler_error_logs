package com.nectar.failurelogsys.db.repository;

import com.nectar.failurelogsys.db.model.ErrorLog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorLogRepository extends JpaRepository<ErrorLog,Long>{
    
    // @Query(value = "INSERT INTO scheduler_failure_logs(date,scheduler_name,type_of_failure,description,id) VALUES (?1,?2,?3,?4,2)", nativeQuery = true)
    // void insertToErrorLog(String date,String scheduler_name,String type_of_failure,String description);


    // "INSERT INTO scheduler_failure_logs " + "(date,scheduler_name,type_of_failure,description) VALUES (?,?,?,?)"
}
