package com.example.myapplication.data.service

import com.example.myapplication.domain.model.NewsResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsServices {
    @GET("news")
    fun getNewsList(
        @Query("pageNumber") pageNumber: Int,
        @Query("pageSize") pageSize: Int,
    ): Call<NewsResponse>
}
