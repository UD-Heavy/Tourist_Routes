package com.example.test.repositories;

import android.os.AsyncTask;

import com.example.test.models.UserDTO;
import com.example.test.exceptions.UserNotFoundException;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AsyncRequest extends AsyncTask<Void, Void, Void> {
    private UserDTO userDTO;

    public AsyncRequest(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    @Override
    protected Void doInBackground(Void[] params) {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            UserService userService = retrofit.create(UserService.class);
            Response<RegisterResponse> response = userService.register(userDTO).execute();

            if (!response.isSuccessful()) {
                ResponseBody registerResponse = response.errorBody();
//                if (registerResponse != null && registerResponse.getMessage().equals("ok"))
                    throw new UserNotFoundException(response.body().getMessage());
            }
        } catch (IOException e) {
            throw new UserNotFoundException("Ошибка соединения с Базой данных");
        }
        return null;
    }

}
