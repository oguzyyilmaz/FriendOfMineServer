package com.msesoft.fom.controller;


import com.msesoft.fom.business.WorkRelationshipBS;
import com.msesoft.fom.domain.WorkRelationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("workrelationship")
public class WorkController {

    @Autowired
    WorkRelationshipBS workRelationshipBS;

    @GetMapping(value = "workType")
    @ResponseBody
    public WorkRelationship findNodeWorkType(@RequestParam("id") String id) {
        return workRelationshipBS.findNodeWorkType(id);
    }

    @GetMapping(value = "workTypePlace")
    @ResponseBody
    public List<WorkRelationship> findNodeWorkTypeForPlace(@RequestParam("placeName") String placeName,@RequestParam("workType") String workType){
        return workRelationshipBS.findNodeWorkTypeForPlace(placeName, workType);
    }

    @GetMapping(value = "findWorkType")
    @ResponseBody
    public List<WorkRelationship> findByWorkType(@RequestParam("workType") String workType){
        return workRelationshipBS.findByWorkType(workType);
    }

    @PostMapping(value = "save")
    @ResponseBody
    public WorkRelationship save (@RequestBody WorkRelationship workRelationship) {
        return workRelationshipBS.save(workRelationship);
    }
}
