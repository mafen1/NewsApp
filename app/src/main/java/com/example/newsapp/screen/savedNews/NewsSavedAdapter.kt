package com.example.newsapp.screen.savedNews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.data.models.ArticlesItem
import com.example.newsapp.databinding.ListNewsItemBinding
import com.example.newsapp.screen.listNews.ListNewsAdapter

class NewsSavedAdapter: ListAdapter<ArticlesItem, NewsSavedAdapter.ViewHolder>(ItemComparator()) {

    inner class ViewHolder(
        private val binding: ListNewsItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(news: ArticlesItem)= with(binding) {

            tvTitle.text = news.title
            tvDescription.text = news.description
            tvSource.text = news.source?.name.toString()
            tvDataTime.text = news.publishedAt

            Glide.with(root)
                .load(news.urlToImage)
                .into(binding.imageView)



        }

    }

    class ItemComparator : DiffUtil.ItemCallback<ArticlesItem>() {
        override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean =
            oldItem.id == newItem.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListNewsItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }



}