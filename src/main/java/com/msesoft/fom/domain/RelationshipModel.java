package com.msesoft.fom.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

/**
 * Created by oguz on 9/5/16.
 */
@RelationshipEntity(type = "Activity")
@Accessors(chain = true)
public class RelationshipModel {

    @GraphId
    private Long id;

    @Getter
    @Setter
    private String relationType;

    @Getter
    @Setter
    @StartNode
    private Person startNode;

    @Getter
    @Setter
    @EndNode
    private ActivityModel endNode;



}