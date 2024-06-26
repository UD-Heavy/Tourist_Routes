package com.example.test.repositories;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AsyncRequest extends AsyncTask<Void, Void, Void> {
    @Override
    protected Void doInBackground(Void[] params) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://reqres.in/") //  http://localhost:8080/api/tasks
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserService userService = retrofit.create(UserService.class);
        Response<UserList> response = null;
        try {
            response = userService.getUserList(2).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        boolean res = response.isSuccessful();
        if (res)
            Log.v("testing", "TRUE");
        else
            Log.v("testing", "FALSE");

        return null;
    }
}
