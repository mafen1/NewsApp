package com.example.newsapp.screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.core.log
import com.example.newsapp.data.models.ArticlesItem
import com.example.newsapp.data.repository.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repositoryImpl: RepositoryImpl
) : ViewModel() {

    private var _listNews = MutableLiveData<MutableList<ArticlesItem>>()
    var listNews: LiveData<MutableList<ArticlesItem>> = _listNews

    // todo rename variables

    private var _listNews1 = MutableLiveData<List<ArticlesItem>>()
    var listNews1: LiveData<List<ArticlesItem>> = _listNews1

    private var _listNews2 = MutableLiveData<List<ArticlesItem>>()
    var listNews2: LiveData<List<ArticlesItem>> = _listNews2


    init {
        news()
        getSavedNews()
    }

    fun getSavedNews() {
        viewModelScope.launch(Dispatchers.IO) {
            val tempNews = repositoryImpl.getNews()
            _listNews.postValue(tempNews)
        }
    }

    fun saveNews(articlesItem: ArticlesItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryImpl.addNews(articlesItem)
        }
    }
    fun deleteNews(){
        viewModelScope.launch(Dispatchers.IO) {
            repositoryImpl.deleteNews()
        }
    }
    fun searchNews(searchQuery: String){
        viewModelScope.launch {
            val temp = repositoryImpl.searchNews(searchQuery)
            log(temp.toString())
            _listNews2.postValue(temp)
        }
    }
    private fun news() {
        viewModelScope.launch(Dispatchers.IO) {
            //todo переименовать переменную
            val a = repositoryImpl.news()
            if (a.isSuccessful) {
                a.body()?.articles.let {
                    _listNews2.postValue(it)
                }
            } else {
                log("error")
            }
        }
    }
}