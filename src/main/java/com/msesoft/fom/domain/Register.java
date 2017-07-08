package com.msesoft.fom.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by oguz on 8/25/16.
 */
public class Register {

    @Getter
    @Setter
    private List<String> contactPhoneList = new ArrayList<>();

    @Setter
    @Getter
    private Person person;
}
