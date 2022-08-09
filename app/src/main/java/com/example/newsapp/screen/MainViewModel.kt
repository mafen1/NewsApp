package com.example.newsapp.screen

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.core.log
import com.example.newsapp.data.models.ArticlesItem
import com.example.newsapp.data.repository.RepositoryImpl
import com.example.newsapp.databinding.FragmentSavedNewsBinding
import com.example.newsapp.screen.listNews.ListNewsAdapter
import com.example.newsapp.screen.savedNews.NewsSavedAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repositoryImpl: RepositoryImpl
) : ViewModel() {

    private var _listSavedNews = MutableLiveData<MutableList<ArticlesItem>>()
    var listSavesNews : LiveData<MutableList<ArticlesItem>> = _listSavedNews

    private var _listNews = MutableLiveData<MutableList<ArticlesItem>>()
    var listNews: LiveData<MutableList<ArticlesItem>> = _listNews

    init {
        news()
        savedNews()
    }

    private fun savedNews() {
        viewModelScope.launch(Dispatchers.IO) {
            val tempNews = repositoryImpl.getNews()
            _listSavedNews.postValue(tempNews)
        }
    }

    fun saveNews(articlesItem: ArticlesItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryImpl.addNews(articlesItem)
            savedNews()
        }
    }

    private fun deleteNews(articlesItem: ArticlesItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryImpl.deleteNews(articlesItem)
            savedNews()
        }
    }

    fun searchNews(searchQuery: String) {
        viewModelScope.launch {
            val temp = repositoryImpl.searchNews(searchQuery)
            _listNews.postValue(temp)
        }
    }

    private fun news() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repositoryImpl.news()
            if (response.isSuccessful) {
                response.body()?.articles.let {
                    _listNews.postValue(it)
                }
            } else {
                log("error")
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun deleteSavedNews(
        viewHolder: RecyclerView.ViewHolder,
        savedNewsAdapter: NewsSavedAdapter,
        binding: FragmentSavedNewsBinding
    ) {
        val position = viewHolder.absoluteAdapterPosition
        val article = savedNewsAdapter.currentList[position]
        deleteNews(article)
        savedNewsAdapter.submitList(_listSavedNews.value)
        Snackbar.make(binding.root, "successful deletion", Snackbar.LENGTH_LONG).apply {
            setAction("Undo") {
                saveNews(article)
                savedNewsAdapter.submitList(_listSavedNews.value)
            }
        }.show()
    }
}