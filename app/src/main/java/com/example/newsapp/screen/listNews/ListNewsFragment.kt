package com.example.newsapp.screen.listNews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.newsapp.R
import com.example.newsapp.core.snackbar
import com.example.newsapp.databinding.FragmentListNewsBinding
import com.example.newsapp.screen.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ListNewsFragment : Fragment() {

    private lateinit var binding: FragmentListNewsBinding
    private val viewModel by viewModels<MainViewModel>()
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
        initData()
    }

    private fun initView() {
        binding.rvNews.adapter = newsAdapter

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.listNews -> snackbar(binding.root, "Вы находитесь уже на данном экране")
                R.id.listStar -> findNavController().navigate(R.id.action_listNewsFragment_to_savedNewsFragment)
            }
            true
        }

        viewModel.listNews.observe(viewLifecycleOwner) {
            if (it != null) {
                newsAdapter.newsList = it
            }
            newsAdapter.notifyDataSetChanged()
        }
    }

    private fun initData() {
        newsAdapter.callBackPosition = { news ->
            findNavController().navigate(
                ListNewsFragmentDirections.actionListNewsFragmentToDescriptionNewsFragment(news)
            )
        }
    }
}