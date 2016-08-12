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
     * mUserName : this is name of user
     */
    private String mUserName;

    /**
     * mUserGmail : this is email of user
     */
    private String mUserGmail;

    /**
     * mUserProfile : this is url image of user
     */
    private int mUserProfile;



}
