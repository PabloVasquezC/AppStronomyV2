package com.example.appstronomyv2;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appstronomyv2.api.ApodResponse;
import com.example.appstronomyv2.api.ApodService;
import com.example.appstronomyv2.api.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiActivity extends AppCompatActivity {
    private static final String API_KEY = "gsmdmboriTgUlxWQQPEJ22YuitgZpqsvS6seAd9O";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ApodService apiService = RetrofitInstance.getRetrofitInstance().create(ApodService.class);

        Call<ApodResponse> call = apiService.getApod(API_KEY, null); // null para obtener la imagen de hoy

        call.enqueue(new Callback<ApodResponse>() {
            @Override
            public void onResponse(Call<ApodResponse> call, Response<ApodResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApodResponse apod = response.body();
                    Log.d("NASA_API", "Título: " + apod.getTitle());
                    Log.d("NASA_API", "Explicación: " + apod.getExplanation());
                    Log.d("NASA_API", "URL: " + apod.getUrl());

                    // Aquí podrías cargar la imagen usando Glide o Coil
                } else {
                    Log.e("NASA_API", "Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApodResponse> call, Throwable t) {
                Log.e("NASA_API", "Fallo: " + t.getMessage());
            }
        });
    }
}