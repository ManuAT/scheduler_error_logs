package com.nectar.failurelogsys.contoller;

import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.nectar.failurelogsys.db.model.ErrorArray;
import com.nectar.failurelogsys.db.model.ErrorLog;
import com.nectar.failurelogsys.db.model.InputData;
import com.nectar.failurelogsys.db.model.OutputData;
import com.nectar.failurelogsys.db.repository.ErrorLogRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
public class ErrorFinderController {
    @Autowired
    ErrorLogRepository errorLogRepository;

    @GetMapping("/")
    public OutputData[] getErrors(@RequestBody InputData inputData) {

        List<ErrorArray> errorArraysList = new ArrayList<ErrorArray>();

        List<OutputData> OutputDataList = new ArrayList<OutputData>();

        for(String equip:inputData.getEquipments()){

            errorArraysList.clear();

            List<ErrorLog> resultList = errorLogRepository.selectFromErrorLogData(new Timestamp(inputData.getStartDate()),new Timestamp(inputData.getEndDate()),
                                                 inputData.getClient(), equip);
            // this loop will be removed ..
            for(ErrorLog result: resultList){
                errorArraysList.add(new ErrorArray(result.getDate().getTime(),result.getTypeOfFailure(),result.getDescription()));
            }

            ErrorArray[] errArray = errorArraysList.stream().toArray(ErrorArray[] ::new);
            OutputDataList.add(new OutputData(equip,errArray));
        }

        OutputData[] outPutArray = OutputDataList.stream().toArray(OutputData[] ::new);
        
        return outPutArray;
        
    }
    
}
