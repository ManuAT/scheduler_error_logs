package com.nectar.failurelogsys.contoller;

import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/equip")
    public List<OutputData>  getErrors(@RequestBody InputData inputData) {


            List<OutputData> OutputDataList = new ArrayList<OutputData>();



            if(inputData.getEquipments().size() != 0){

                List<ErrorLog> resultList = errorLogRepository.selectFromErrorLogData(new Timestamp(inputData.getStartDate()),new Timestamp(inputData.getEndDate()),
                                                    inputData.getClient(), inputData.getEquipments());

                for(String equip:inputData.getEquipments())
                {
                    List<ErrorArray> errorArraysList =  resultList.stream()
                            .filter(p -> p.getEquipmentName().equals(equip))
                            .map(p -> new ErrorArray(p.getDate().getTime(), p.getTypeOfFailure(),p.getDescription()))
                            .collect(Collectors.toList());
  
                    OutputDataList.add(new OutputData(equip,errorArraysList));

                }

           
            }
            else{
        
            List<ErrorLog> resultList = errorLogRepository.selectFromErrorLogDataWithDomain(new Timestamp(inputData.getStartDate()),new Timestamp(inputData.getEndDate()),
                                                         inputData.getClient());
                                                         
            List<String> uniqueEquipment = resultList.stream().map(ErrorLog::getEquipmentName).distinct().collect(Collectors.toList());

            for(String equip:uniqueEquipment)
            {
                List<ErrorArray> errorArraysList =  resultList.stream()
                    .filter(p -> p.getEquipmentName().equals(equip))
                    .map(p -> new ErrorArray(p.getDate().getTime(), p.getTypeOfFailure(),p.getDescription()))
                    .collect(Collectors.toList());

                OutputDataList.add(new OutputData(equip,errorArraysList));
            }
            
            }

        return OutputDataList;
        
    }

    
    
}
