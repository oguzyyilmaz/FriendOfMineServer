package com.msesoft.fom.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by oguz on 28.07.2016.
 */
public class Token {
    @Getter
    @Setter
    private String token;

    @Getter
    @Setter
    private boolean isNew;

}
