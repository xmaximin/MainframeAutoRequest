package com.codeattitude.mainframeautorequest.api;

import com.codeattitude.mainframeautorequest.model.IndexSummary;
import com.codeattitude.mainframeautorequest.model.User;
import com.google.gson.Gson;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by xaviermaximin on 10/10/2015.
 */
public interface UserService {

    @GET("authenticate")
    Call<User> getUser(@Header("Authorization") String authorization);



}
