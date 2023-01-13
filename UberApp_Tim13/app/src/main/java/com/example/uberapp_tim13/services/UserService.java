package com.example.uberapp_tim13.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.uberapp_tim13.dtos.RideReturnedDTO;
import com.example.uberapp_tim13.dtos.UserDTO;
import com.example.uberapp_tim13.rest.RestUtils;
import com.example.uberapp_tim13.rest.UserAPI;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserService {
   public UserDTO returnedUser;

   public void getUserByEmail(String email) {
       Call<UserDTO> call = RestUtils.userApi.getUserByEmail(email);
       call.enqueue(new Callback<UserDTO>() {
           @Override
           public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
               if (response.code() == 200) {
                   Log.d("REZ", response.body().toString());
                   returnedUser = response.body();
               }
           }

           @Override
           public void onFailure(Call<UserDTO> call, Throwable t) {
               returnedUser = null;
               Log.d("REZ", t.getMessage() != null ? t.getMessage() : "error");
           }
       });
   }
}