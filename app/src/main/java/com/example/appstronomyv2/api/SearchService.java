package com.example.appstronomyv2.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchService {
    @GET("Search")
    Call<ApodResponse> getSearch(
            @Query("keyword") String keyword,
            @Query("mediaType") String mediaType
    );
}