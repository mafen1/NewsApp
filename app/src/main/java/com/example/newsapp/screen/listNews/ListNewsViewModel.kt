package com.example.newsapp.screen.listNews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.models.ArticlesItem
import com.example.newsapp.data.repository.RepositoryImpl
import com.example.newsapp.core.log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListNewsViewModel @Inject constructor(
    private val repositoryImpl: RepositoryImpl
) : ViewModel() {

    private var _listNews = MutableLiveData<List<ArticlesItem>>()
    var listNews: LiveData<List<ArticlesItem>> = _listNews

    init {
        news()
    }

    private fun news() {
        viewModelScope.launch(Dispatchers.IO) {
            //todo переименовать переменную
            val a = repositoryImpl.news()
            if (a.isSuccessful) {
                a.body()?.articles.let {
                    _listNews.postValue(it)
                }
            } else {
                log("error")
            }
        }
    }
}