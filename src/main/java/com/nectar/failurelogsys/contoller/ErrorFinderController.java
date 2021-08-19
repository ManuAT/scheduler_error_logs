package com.nectar.failurelogsys.contoller;

import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.nectar.failurelogsys.db.model.ErrorArray;
import com.nectar.failurelogsys.db.model.ErrorLog;
import com.nectar.failurelogsys.db.model.InputData;
import com.nectar.failurelogsys.db.model.OutputData;
import com.nectar.failurelogsys.db.repository.ErrorLogRepository;

import org.hibernate.result.Output;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
public class ErrorFinderController {
    @Autowired
    ErrorLogRepository errorLogRepository;

    @GetMapping("/equip")
    public List<OutputData>  getErrors(@RequestBody InputData inputData) {


            List<OutputData> OutputDataList = new ArrayList<OutputData>();

            List<ErrorLog> resultList = new ArrayList<ErrorLog>();

            if(inputData.getEquipments().size() != 0)
                resultList = errorLogRepository.selectFromErrorLogData(new Timestamp(inputData.getStartDate()),new Timestamp(inputData.getEndDate()),inputData.getClient(), inputData.getEquipments());
            else
                resultList = errorLogRepository.selectFromErrorLogDataWithDomain(new Timestamp(inputData.getStartDate()),new Timestamp(inputData.getEndDate()),inputData.getClient());

            Map<String, List<ErrorLog>> resultGrouped = resultList.stream().collect(Collectors.groupingBy(w -> w.getEquipmentName()));

            OutputDataList = resultGrouped.keySet().stream().sorted().map(p-> new OutputData(p,resultGrouped.get(p).stream().map(m-> new ErrorArray(m.getDate().getTime(), m.getTypeOfFailure(),m.getDescription())).collect(Collectors.toList()))).collect(Collectors.toList());
        
        return OutputDataList;
        
    }

    
    
}
