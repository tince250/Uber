package com.example.uberapp_tim13.rest;

import com.example.uberapp_tim13.dtos.CredentialsDTO;
import com.example.uberapp_tim13.dtos.TokenDTO;
import com.example.uberapp_tim13.dtos.UserDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserAPI {

    @GET("user/{id}")
    Call<UserDTO> doGetUser(@Header("Authorization") String token,
                            @Path("id") int id);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST(RestUtils.LOGIN)
    Call<TokenDTO> login(@Body CredentialsDTO credentialsDTO);
}
