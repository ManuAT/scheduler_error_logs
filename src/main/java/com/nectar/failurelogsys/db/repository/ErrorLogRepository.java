package com.nectar.failurelogsys.db.repository;

import com.nectar.failurelogsys.db.model.ErrorLog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorLogRepository extends JpaRepository<ErrorLog,Long>{

}
