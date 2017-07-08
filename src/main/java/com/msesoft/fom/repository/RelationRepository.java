package com.msesoft.fom.repository;

import com.msesoft.fom.domain.RelationshipModel;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by oguz on 9/5/16.
 */
public interface RelationRepository extends GraphRepository<RelationshipModel> {

    @Query("Match (n:Person{token: {token}})" +
            "-[p:Activity]-(r:Activity{uniqueId: {uniqueId}})" +
            " DETACH DELETE p")
    void notJoinActivity(@Param("token") String token, @Param("uniqueId") String uniqueId);
}
