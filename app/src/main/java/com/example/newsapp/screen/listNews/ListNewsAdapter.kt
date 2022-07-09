package com.example.newsapp.screen.listNews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.data.models.ArticlesItem
import com.example.newsapp.databinding.ListNewsItemBinding
import com.example.notesapp.core.log

class ListNewsAdapter : ListAdapter<ArticlesItem, ListNewsAdapter.ViewHolder>(ItemComparator()) {

     var newsList = listOf<ArticlesItem>()

    inner class ViewHolder(private val binding: ListNewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(news: ArticlesItem) {

            binding.tvTitle.text = news.title
            binding.tvDescription.text = news.description
            binding.tvSource.text = news.source.toString()
            binding.tvDataTime.text = news.publishedAt

            Glide.with(binding.root)
                .load(news.urlToImage)
                .into(binding.imageView)
        }
    }

    class ItemComparator : DiffUtil.ItemCallback<ArticlesItem>() {
        override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        log(newsList.size.toString())
        return ViewHolder(
            ListNewsItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(currentList[position])

    override fun getItemCount(): Int = newsList.size
}