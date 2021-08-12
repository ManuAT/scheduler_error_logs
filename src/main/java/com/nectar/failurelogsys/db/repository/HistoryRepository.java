package com.nectar.failurelogsys.db.repository;
import com.nectar.failurelogsys.db.model.ErrorLog;
import com.nectar.failurelogsys.db.model.HistoryData;
import com.nectar.failurelogsys.db.model.AggregationData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
// import org.springframework.jdbc.support.GeneratedKeyHolder;
// import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;





@Repository
public class HistoryRepository{
    @Autowired 
    private JdbcTemplate jdbcTemplateTwo;
    
    public void insert(HistoryData historyData)
	{
		String sql = "INSERT INTO HistoryData " + "(name,datetime,data) VALUES (?,?,?)";


		// KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplateTwo.update(new PreparedStatementCreator()
		{
			@Override
			public PreparedStatement createPreparedStatement(Connection connection)
					throws SQLException
			{
				PreparedStatement ps = connection.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, historyData.getName());
				ps.setString(2, historyData.getDatetime());
				ps.setString(3, historyData.getData());
				return ps;
			}
		});

	

		// int generatedEmployeeId = holder.getKey().intValue();
		// System.out.println("generated EmployeeId = " + generatedEmployeeId);
	}
    
	public void insertToErrorLog(ErrorLog errorLog){
		String sql = "INSERT INTO scheduler_failure_logs " + "(date,scheduler_name,type_of_failure,description) VALUES (?,?,?,?)";
		jdbcTemplateTwo.update(new PreparedStatementCreator()
		{
			@Override
			public PreparedStatement createPreparedStatement(Connection connection)
					throws SQLException
			{
				PreparedStatement ps = connection.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, errorLog.getDate());
				ps.setString(2, errorLog.getScheduler_name());
				ps.setString(3, errorLog.getType_of_failure());
				ps.setString(4, errorLog.getDescription());
				return ps;
			}
		});
	}

	public void insertToAggregation(AggregationData aggregationData ){
		String sql = "INSERT INTO aggregationdata " + "(name,datetime,consumption) VALUES (?,?,?)";
		jdbcTemplateTwo.update(new PreparedStatementCreator()
		{
			@Override
			public PreparedStatement createPreparedStatement(Connection connection)
					throws SQLException
			{
				PreparedStatement ps = connection.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, aggregationData.getName());
				ps.setString(2, aggregationData.getDatetime());
				ps.setString(3, aggregationData.getConsumption());
				return ps;
			}
		});
	}

	public List<HistoryData> selectFromHistoryData(String startDate, String endDate){
		// SELECT  * FROM    public.historydata WHERE   datetime >= '2013-01-03' AND datetime   <= '2013-01-09'
		String sql = "SELECT * FROM historydata " +"WHERE  datetime >= '"+startDate+"' AND datetime < '"+endDate+"'";
		try{
		List<HistoryData> result = jdbcTemplateTwo.query(sql,new HistoryDataMapper());
		return result;
		}catch(Exception e){
		System.out.println(e+ "Error Ocoured on sql query");
		return null;
		}
		
	}

	public AggregationData selectFromAggregationData(String startDate, String equipmentName){

		String sql = "SELECT * FROM aggregationdata " +"WHERE  datetime = '"+startDate+"' AND name = '"+equipmentName+"'";
		try{
		AggregationData result = jdbcTemplateTwo.queryForObject(sql,AggregationData.class);
		return result;
		}catch(Exception e){
		System.out.println(e+ "Error Ocoured on sql query");
		return null;
		}
		
	}

	


    
}
