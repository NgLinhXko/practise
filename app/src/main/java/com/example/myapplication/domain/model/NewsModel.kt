package com.example.myapplication.domain.model

data class NewsModel(
    var id: String = "",
    var title: String = "",
    var description: String = "",
    var author: String = "",
    var images: List<String> = mutableListOf(),
    var detailUrl: String = "",
)
