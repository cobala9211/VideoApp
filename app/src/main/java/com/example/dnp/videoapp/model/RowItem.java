package com.example.dnp.videoapp.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Created by dnp on 10/08/2016.
 */

@RequiredArgsConstructor(suppressConstructorProperties = true, access = AccessLevel.PUBLIC)
@AllArgsConstructor(suppressConstructorProperties = true, access = AccessLevel.PUBLIC)
@Data
public class RowItem {

    /**
     * url : this is url image row item
     **/
    private int url;

    /**
     * title : this is title row item
     **/
    private String title;

}
