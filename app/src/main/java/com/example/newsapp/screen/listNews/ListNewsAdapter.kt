package com.example.newsapp.screen.listNews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.core.log
import com.example.newsapp.data.models.ArticlesItem
import com.example.newsapp.databinding.ListNewsItemBinding

class ListNewsAdapter : ListAdapter<ArticlesItem, ListNewsAdapter.ViewHolder>(ItemComparator()) {

    var newsList = mutableListOf<ArticlesItem>()

    var callBackPosition: ((news: ArticlesItem) -> Unit)? = null

    inner class ViewHolder(
        private val binding: ListNewsItemBinding
        ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(news: ArticlesItem) {

            binding.tvTitle.text = news.title
            binding.tvDescription.text = news.description
            binding.tvSource.text = news.source?.name.toString()
            binding.tvDataTime.text = news.publishedAt

            Glide.with(binding.root)
                .load(news.urlToImage)
                .into(binding.imageView)

            binding.root.setOnClickListener {
                callBackPosition?.invoke( newsList[absoluteAdapterPosition])
            }

            val callBack = object : ItemTouchHelper.SimpleCallback(0,  ItemTouchHelper.RIGHT){
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    callBackPosition?.invoke( newsList[absoluteAdapterPosition])
                    log(news.id.toString())
                }
            }
            val helper = ItemTouchHelper(callBack)
        }

    }

    class ItemComparator : DiffUtil.ItemCallback<ArticlesItem>() {
        override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListNewsItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(newsList[position])

    override fun getItemCount(): Int = newsList.size
}