package com.example.test.repositories;

import com.example.test.models.UserDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserService {
    @POST("/api/register")
    Call<RegisterResponse> register(@Body UserDTO userDTO);

    @GET("/api/login")
    Call<RegisterResponse> login(@Body UserDTO userDTO);
}
