package com.nectar.failurelogsys.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nectar.failurelogsys.db.beans.ErrorLog;



public interface ErrorFinderRepository extends JpaRepository<ErrorLog,Long> {
	
	
	
	
	

}
