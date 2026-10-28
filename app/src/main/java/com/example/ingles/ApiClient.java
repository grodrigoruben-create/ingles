package com.example.ingles;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String BASE_URL = "http://192.168.1.64/ingles-api/public/";
    private static Retrofit retrofit = null;

    // AHORA PEDIMOS EL CONTEXTO PARA PODER LEER LAS PREFERENCIAS
    public static ApiService getClient(Context context) {
        if (retrofit == null) {

            // 1. Configuramos el Interceptor
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request peticionOriginal = chain.request();

                    // 2. Leemos el token guardado en el teléfono
                    SharedPreferences preferences = context.getSharedPreferences("AppInglesPrefs", Context.MODE_PRIVATE);
                    String token = preferences.getString("JWT_TOKEN", null);

                    // 3. Si hay un token, lo inyectamos en la cabecera
                    if (token != null) {
                        Request peticionConToken = peticionOriginal.newBuilder()
                                .header("Authorization", "Bearer " + token)
                                .build();
                        return chain.proceed(peticionConToken);
                    }

                    // Si no hay token (ej. al registrarse), la dejamos pasar normal
                    return chain.proceed(peticionOriginal);
                }
            }).build();

            // 4. Construimos Retrofit agregando el 'client'
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client) // <-- Le pasamos el interceptor
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiService.class);
    }
}