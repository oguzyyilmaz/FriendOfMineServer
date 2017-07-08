package com.msesoft.fom.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.StartNode;


@Accessors(chain = true)
public class CustomFriendRelationship {

    @Getter
    @Setter
    private String friendType;

    @Getter
    @Setter
    @StartNode
    private CustomPerson startNode;

    @Getter
    @Setter
    @EndNode
    private CustomPerson endNode;
}
