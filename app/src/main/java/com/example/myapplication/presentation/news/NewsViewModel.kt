package com.example.myapplication.presentation.news

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.NewsModel
import com.example.myapplication.domain.model.NewsResponse
import com.example.myapplication.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {
    private val _newsList = MutableLiveData<List<NewsModel>>()
    var newsList: LiveData<List<NewsModel>> = _newsList


    fun getNewsList(pageNumber: Int = 1, pageSize: Int = 10) {
        viewModelScope.launch (Dispatchers.IO){
            try {
                val response = newsRepository.getNewsList(pageNumber = pageNumber, pageSize = pageSize).execute()
                Log.d("TAG", "getNewsList: onCreate ${response.body()?.data?.size}")
                if (response.isSuccessful && response.body() != null) {
                    _newsList.value = response.body()!!.data
                    Log.d("TAG", "getNewsList: ${response.body()!!.data}")
                } else {
                    Log.d("TAG", "getNewsList: gdasgda")
                }
            } catch (e: Exception) {
                Log.d("TAG", "getNewsList: $e")
            }
        }
    }
}
