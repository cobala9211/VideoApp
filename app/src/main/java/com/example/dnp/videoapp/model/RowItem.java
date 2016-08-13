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

    private int url;

    private String title;

}
