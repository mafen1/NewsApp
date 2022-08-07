package com.example.newsapp.screen.searchNews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.newsapp.R
import com.example.newsapp.core.log
import com.example.newsapp.core.snackbar
import com.example.newsapp.databinding.FragmentSearchNewsBinding
import com.example.newsapp.screen.MainViewModel
import com.example.newsapp.screen.listNews.ListNewsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchNewsFragment : Fragment() {

    lateinit var binding: FragmentSearchNewsBinding
    private val adapter = ListNewsAdapter()
    private val viewModel by viewModels<MainViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchNewsBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {

        binding.searchView.isSubmitButtonEnabled = true
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    log(newText)
                    searchDatabase(newText)
                }
                return true
            }

        })

        // setup rcView
        binding.recyclerView.adapter = adapter
        binding.recyclerView.itemAnimator = null



        viewModel.listNews2.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.newsList = it
            }
            adapter.notifyDataSetChanged()
        }

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.listNews -> findNavController().navigate(R.id.action_searchNewsFragment_to_listNewsFragment)
                R.id.listStar -> findNavController().navigate(R.id.action_searchNewsFragment_to_savedNewsFragment)
                R.id.listSearch -> snackbar(binding.root, "Вы находитесь уже на данном экране")
            }
            true
        }
    }

   }


}