package com.example.carwash.network;

import com.example.carwash.entities.AccessToken;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @POST("register")
    @FormUrlEncoded
    Call<AccessToken> register(@Field("name") String name, @Field("email") String email, @Field("password") String password,@Field("phone") String phone);

    @POST("login")
    @FormUrlEncoded
    Call<AccessToken> login(@Field("username") String name, @Field("password") String password);
}
