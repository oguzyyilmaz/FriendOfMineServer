package com.msesoft.fom.repository;


import com.msesoft.fom.domain.WorkRelationship;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by kerse on 21.06.2016.
 */
public interface WorkRepository extends GraphRepository<WorkRelationship> {


    List<WorkRelationship> findByWorkType(String workType);

    @Query("MATCH p =((:Place {uniqueId: {uniqueId} })-[x:WORK {workType: {workType} }]-()) return x")
    List<WorkRelationship> findNodeWorkTypeForPlace(@Param("uniqueId") String placeName, @Param("workType") String workType);

    @Query("MATCH p =((:Person {uniqueId: {uniqueId} })-[x:WORK]-()) return x")
    WorkRelationship findNodeWorkType(@Param("uniqueId") String nodeName);

    WorkRelationship save(WorkRelationship work);

}

