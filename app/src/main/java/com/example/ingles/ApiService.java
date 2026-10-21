package com.example.ingles;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    // Se eliminó el @Header, solo se envía el Body (los datos del alumno)
    @POST("api/alumno")
    Call<ApiResponse> gardaAlumno(@Body Alumno alumno);
}