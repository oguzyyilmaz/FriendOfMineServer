package com.msesoft.fom.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oguz on 9/5/16.
 */
@NodeEntity(label = "Activity")
@Accessors(chain = true)
public class ActivityModel {

    @GraphId
    @Getter
    Long id;

    @Getter
    @Setter
    private String uniqueId;

    @Getter
    @Setter
    private String personUniqueId;

    @Getter
    @Setter
    private String activityArea;

    @Getter
    @Setter
    private String activityName;

    @Getter
    @Setter
    private String activityDate;

    @Getter
    @Setter
    private String activityPhoto;

    @Getter
    @Setter
    private String activityPlaces;

    @Getter
    @Setter
    private String activityDescription;

    @Getter
    @Setter
    private List<String> activityJoinPerson = new ArrayList<>();

    @Getter
    @Setter
    private String photoId;

    @Getter
    @Setter
    private List<String> joinList = new ArrayList<>();
}
