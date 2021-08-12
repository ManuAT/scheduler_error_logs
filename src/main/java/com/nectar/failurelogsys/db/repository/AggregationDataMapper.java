package com.nectar.failurelogsys.db.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.nectar.failurelogsys.db.model.AggregationData;

import org.springframework.jdbc.core.RowMapper;

public class AggregationDataMapper implements RowMapper<AggregationData> {

    public AggregationData mapRow(ResultSet rs, int rowNum) throws SQLException {
        AggregationData aggregationData = new AggregationData();
        aggregationData.setName(rs.getString("name"));
        aggregationData.setDatetime(rs.getString("datetime"));
        aggregationData.setConsumption(rs.getString("consumption"));
        return aggregationData;
     }
    
}
