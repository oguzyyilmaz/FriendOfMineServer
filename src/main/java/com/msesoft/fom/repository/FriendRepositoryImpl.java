package com.msesoft.fom.repository;

import com.msesoft.fom.domain.FriendRelationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendRepositoryImpl implements FriendRepositoryCustom {

    private Neo4jOperations neo4jOperations;

    @Autowired
    public FriendRepositoryImpl(Neo4jOperations neo4jTemplate){
        this.neo4jOperations = neo4jTemplate;
    }

    @Override
    public List<FriendRelationship> friendWay(String startNode, String endNode) {
        List<FriendRelationship> list = new ArrayList<FriendRelationship>();
        String query = null;
        for (int i=0;i<2;i++){
        if(i==0){
            query = "MATCH (:Person{token: {startNode} })-->()-[h]->(:Person{uniqueId: {endNode} }) return h ";
        }
        if(i==1){
            query = "MATCH (:Person{token: {startNode} })-->()-[h]->()-->(:Person{uniqueId: {endNode} }) return h ";
        }
        Map<String,Object> params = new HashMap<>();
        params.put("startNode", startNode);
        params.put("endNode", endNode);



        for (FriendRelationship friendRelationship:neo4jOperations.queryForObjects(FriendRelationship.class,query,params)){
            list.add(friendRelationship);
        }}
        return list;
    }
}
