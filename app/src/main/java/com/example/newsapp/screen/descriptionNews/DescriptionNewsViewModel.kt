package com.example.newsapp.screen.descriptionNews

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
class DescriptionNewsViewModel @Inject constructor(
    private val repositoryImpl: RepositoryImpl
): ViewModel() {

    private var _listSavedNews = MutableLiveData<MutableList<ArticlesItem>>()
    var listSavedNews: LiveData<MutableList<ArticlesItem>> = _listSavedNews

    fun getSavedNews(){
        viewModelScope.launch(Dispatchers.IO) {
            val tempNews = repositoryImpl.getNews()
            _listSavedNews.postValue(tempNews)
            log(_listSavedNews.value.toString())
        }
    }

    fun saveNews(articlesItem: ArticlesItem){
        viewModelScope.launch(Dispatchers.IO) {
            repositoryImpl.addNews(articlesItem)
            log(_listSavedNews.value.toString())
        }
    }
}