package com.example.newsapp.screen.savedNews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.newsapp.R
import com.example.newsapp.core.log
import com.example.newsapp.core.snackbar
import com.example.newsapp.databinding.FragmentSavedNewsBinding
import com.example.newsapp.screen.MainActivity
import com.example.newsapp.screen.MainViewModel
import com.example.newsapp.screen.listNews.ListNewsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedNewsFragment : Fragment() {
    lateinit var binding: FragmentSavedNewsBinding
    private val savedNewsAdapter = ListNewsAdapter()
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedNewsBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }
    private fun initView(){

        binding.bottomNavigationView.menu.findItem(R.id.listStar).isChecked = true
        binding.rvNews.adapter = savedNewsAdapter


        viewModel.listNews2.observe(viewLifecycleOwner){
            if (it != null){
                savedNewsAdapter.newsList = it
            }
        }

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.listNews -> findNavController().navigate(R.id.action_savedNewsFragment_to_listNewsFragment)
                R.id.listStar -> snackbar(binding.root, "Вы находитесь уже на данном экране")
                R.id.listSearch -> findNavController().navigate(R.id.action_savedNewsFragment_to_searchNewsFragment)
            }
            true
        }
    }

}