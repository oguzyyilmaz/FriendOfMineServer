package com.msesoft.fom.business;

import com.msesoft.fom.repository.WorkRepository;
import com.msesoft.fom.domain.WorkRelationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkRelationshipBS {

    @Autowired
    WorkRepository workRepository;

    public WorkRelationship findNodeWorkType(String id){

        return workRepository.findNodeWorkType(id);

    }

    public List<WorkRelationship> findNodeWorkTypeForPlace(String placeName,String workType){

        return workRepository.findNodeWorkTypeForPlace(placeName,workType);

    }

    public List<WorkRelationship> findByWorkType(String type){

        return workRepository.findByWorkType(type);

    }

    public WorkRelationship save(WorkRelationship workRelationship) {
        return workRepository.save(workRelationship);
    }
}
