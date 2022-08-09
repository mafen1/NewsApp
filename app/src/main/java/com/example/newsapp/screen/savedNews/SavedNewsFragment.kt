package com.example.newsapp.screen.savedNews

import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.core.snackbar
import com.example.newsapp.databinding.FragmentSavedNewsBinding
import com.example.newsapp.screen.BaseFragment
import com.example.newsapp.screen.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedNewsFragment : BaseFragment<FragmentSavedNewsBinding>(FragmentSavedNewsBinding::inflate) {

    private val savedNewsAdapter = NewsSavedAdapter()
    private val viewModel by viewModels<MainViewModel>()

    override fun initView() {

        binding.bottomNavigationView.menu.findItem(R.id.listStar).isChecked = true
        binding.rvNews.adapter = savedNewsAdapter
        savedNewsAdapter.submitList(viewModel.listSavesNews.value)

        binding.searchView.isSubmitButtonEnabled = true
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) searchDatabase(newText)
                return true
            }

        })


        viewModel.listSavesNews.observe(viewLifecycleOwner) {
            if (it != null) {
                savedNewsAdapter.submitList(it)
            }
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

        ItemTouchHelper(callBack).apply {
            attachToRecyclerView(binding.rvNews)
        }
    }

    private fun searchDatabase(query: String) {
        val searchQuery = "%$query%"

        viewModel.searchNews(searchQuery)

        viewModel.listSavesNews.observe(this) {
            savedNewsAdapter.submitList(it)
        }
    }


}