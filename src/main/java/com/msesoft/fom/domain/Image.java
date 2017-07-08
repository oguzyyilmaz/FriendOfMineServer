package com.msesoft.fom.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by oguz on 01.08.2016.
 */
@Accessors(chain = true)
public class Image {

    @Setter
    @Getter
    private String base64String;

    @Getter
    @Setter
    private String token;
}
