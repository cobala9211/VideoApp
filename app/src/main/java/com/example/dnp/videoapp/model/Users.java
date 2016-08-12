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

    /**
     * userName : this is name of user
     */
    private String userName;

    /**
     * email : this is email of user
     */
    private String email;

    /**
     * userProfile : this is url image of user
     */
    private int userProfile;



}
