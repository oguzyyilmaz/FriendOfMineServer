package com.msesoft.fom.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by oguz on 27.07.2016.
 */
public class MobileSession {


    @Getter
    @Setter
    private String uniqueId;

    @Setter
    @Getter
    private Date createdDate;

    @Getter
    @Setter
    private String token;
}
