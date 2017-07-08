package com.msesoft.fom.repository;


import com.msesoft.fom.domain.Person;
import com.msesoft.fom.domain.Places;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface PlacesRepository extends GraphRepository<Places> {


    List<Places> findByType(String type);

    @Query("MATCH p=((:Person{uniqueId: {uniqueId} })-[:WORK]-(r)) return r")
    Places workSearch(@Param("uniqueId") String uniqueId);

    @Query("MATCH p =((:Places {uniqueId: {uniqueId} })-[:WORK]-(n)) return n")
    List<Person> findWorkAllNode(@Param("uniqueId") String place);

    @Query("MATCH (n:Places{uniqueId : {uniqueId} }) DETACH Delete n")
    void deletePlaces(String uniqueId);

    Places findByName(String name);

    Places findByUniqueId(String uniqueId);
}
