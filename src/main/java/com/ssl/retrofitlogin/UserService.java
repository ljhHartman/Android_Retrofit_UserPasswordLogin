package com.ssl.retrofitlogin;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {


    @POST("/login")
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);


}