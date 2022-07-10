package com.example.newsapp.screen.listNews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.newsapp.databinding.FragmentListNewsBinding
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

    private fun initView() {
        binding.rvNews.adapter = newsAdapter

        viewModel.listNews.observe(viewLifecycleOwner) {
            if (it != null) {
                newsAdapter.newsList = it
            }
            newsAdapter.notifyDataSetChanged()
        }
    }
}