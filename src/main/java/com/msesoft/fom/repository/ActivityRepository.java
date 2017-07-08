package com.msesoft.fom.repository;

import com.msesoft.fom.domain.ActivityModel;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by oguz on 9/5/16.
 */
public interface ActivityRepository extends GraphRepository<ActivityModel> {

    @Query("MATCH p=((n:Person{token: {token} })-[:FRIEND*3..3 ]->(w:Person))\n" +
            "                    with n as nNode,w as wNode , count(*) as xCount\n" +
            "                    where not wNode = nNode and not (n:Person{token: {token}  })-[:FRIEND*1..1]->(w:Person)\n" +
            "MATCH (m:Activity) where (wNode.uniqueId in m.personUniqueId) and m.activityArea='all'\n" +
            "               return m")
    List<ActivityModel> degreeActivity(@Param("token") String token);

    @Query("MATCH (n:Person{token: {token}})-[FRIEND]-(p)\n" +
            "MATCH (m:Activity) where (p.uniqueId in m.personUniqueId) return m")
    List<ActivityModel> friendActivity(@Param("token") String token);

    List<ActivityModel> findByPersonUniqueId(String uniqueId);

    ActivityModel findByUniqueId(String uniqueId);


}
