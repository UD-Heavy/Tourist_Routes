package com.example.test.repositories;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserService {
    @GET("/api/users")
    Call<UserList> getUserList(@Query("page") int pageParam);
}
