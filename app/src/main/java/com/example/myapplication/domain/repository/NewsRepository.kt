package com.example.myapplication.domain.repository

import com.example.myapplication.data.service.NewsServices
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val apiService: NewsServices
) {
     fun getNewsList(pageNumber: Int, pageSize: Int) =
        apiService.getNewsList(pageNumber = pageNumber, pageSize = pageSize)
}
