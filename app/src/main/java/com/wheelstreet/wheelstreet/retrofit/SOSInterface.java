package com.wheelstreet.wheelstreet.retrofit;


import com.wheelstreet.wheelstreet.model.Questions;
import com.wheelstreet.wheelstreet.model.Response;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


/**
 * This interface is used to send the parameter and returns call back for the response.
 */

public interface SOSInterface {

    @GET("/v1/test/questions")
    Call<Questions> getPhotos();
    @POST("/v1/test/answers")
    Call<Response> sendQuestionsAndAnswers(@Body JSONObject hashMap);


}
