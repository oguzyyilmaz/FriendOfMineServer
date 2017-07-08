package com.msesoft.fom.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

import java.util.ArrayList;

/**
 * Created by oguz on 18.06.2016.
 */
@NodeEntity(label = "Person")
@Accessors(chain = true)
public class Person {

    @GraphId
    @Getter
    private Long id;

    @Getter
    @Setter
    private String uniqueId;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String phoneNumber;

    @Getter
    @Setter
    private String password;

    @Setter
    @Getter
    private String firstName;

    @Setter
    @Getter
    private String lastName;

    @Getter
    @Setter
    private String gender;

    @Setter
    @Getter
    private String hoby;

    @Setter
    @Getter
    private String photo;

    @Setter
    @Getter
    private String deviceID;

    @Getter
    @Setter
    private String token;

    @Getter
    @Setter
    private String occupation;

    @Getter
    @Setter
    private String school;

    @Getter
    @Setter
    private ArrayList<String> photoList = new ArrayList<>();

    @Getter
    @Setter
    private ArrayList<String> notifyList = new ArrayList<>();

    @Getter
    @Setter
    private float popular;

    @Getter
    @Setter
    private boolean active;



}
