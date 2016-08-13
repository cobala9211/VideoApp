package com.example.dnp.videoapp.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Created by dnp on 11/08/2016.
 */
@RequiredArgsConstructor(suppressConstructorProperties = true, access = AccessLevel.PUBLIC)
@AllArgsConstructor(suppressConstructorProperties = true, access = AccessLevel.PUBLIC)
@Data
public class Users {

    private String userName;

    private String email;

    private int userProfile;

}
