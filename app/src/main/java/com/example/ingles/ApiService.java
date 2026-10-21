package com.example.ingles;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {

    @POST("api/alumno")
    Call<ApiResponse> gardaAlumno(@Header("Authorization") String token, @Body Alumno alumno);
}
