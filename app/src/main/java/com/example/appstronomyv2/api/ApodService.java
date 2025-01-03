package com.example.appstronomyv2.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApodService {
    @GET("planetary/apod")
    Call<ApodResponse> getApod(
            @Query("api_key") String apiKey,
            @Query("date") String date
    );
}