package com.example.newsapp.screen.savedNews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.core.snackbar
import com.example.newsapp.databinding.FragmentSavedNewsBinding
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

    private fun initView() {

        binding.bottomNavigationView.menu.findItem(R.id.listStar).isChecked = true
        binding.rvNews.adapter = savedNewsAdapter

        binding.searchView.isSubmitButtonEnabled = true
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {

                    searchDatabase(newText)
                }
                return true
            }

        })


        viewModel.listNews.observe(viewLifecycleOwner) {
            if (it != null) {
                savedNewsAdapter.newsList = it
            }
            savedNewsAdapter.notifyDataSetChanged()
        }

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.listNews -> findNavController().navigate(R.id.action_savedNewsFragment_to_listNewsFragment)
                R.id.listStar -> snackbar(binding.root, "Вы находитесь уже на данном экране")

            }
            true
        }

        val callBack = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.deleteSavedNews(viewHolder, savedNewsAdapter, binding)
            }
        }

        val helper = ItemTouchHelper(callBack).attachToRecyclerView(binding.rvNews)
    }

    private fun searchDatabase(query: String) {
        val searchQuery = "%$query%"

        viewModel.searchNews(searchQuery)
        viewModel.listNews.observe(this) {
            savedNewsAdapter.newsList = it
            savedNewsAdapter.notifyDataSetChanged()
        }
    }


}