// package com.nectar.failurelogsys.db.repository;

// import java.sql.ResultSet;
// import java.sql.SQLException;

// import com.nectar.failurelogsys.db.model.HistoryData;

// import org.springframework.jdbc.core.RowMapper;

// public class HistoryDataMapper implements RowMapper<HistoryData>{

//     public HistoryData mapRow(ResultSet rs, int rowNum) throws SQLException {
//         HistoryData historyData = new HistoryData();
//         historyData.setName(rs.getString("name"));
//         historyData.setDatetime(rs.getString("datetime"));
//         historyData.setData(rs.getString("data"));
//         return historyData;
//      }
    
// }
