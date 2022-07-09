package com.example.newsapp.screen.listNews

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentListNewsBinding
import com.example.notesapp.core.log
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ListNewsFragment : Fragment() {

    lateinit var binding: FragmentListNewsBinding
    private val viewModel by viewModels<ListNewsViewModel>()
    private val newsAdapter = ListNewsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListNewsBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }
    private fun initView(){

        binding.rvNews.adapter = newsAdapter

        viewModel.listNews.observe(viewLifecycleOwner){
            if(it != null){
                log(it.toString())
                newsAdapter.submitList(it)
                log(newsAdapter.newsList.size.toString())
            }
            newsAdapter.notifyDataSetChanged()
        }
    }
}