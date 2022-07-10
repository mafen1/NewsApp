package com.example.newsapp.screen.descriptionNews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.newsapp.core.log
import com.example.newsapp.screen.MainActivity
import com.example.newsapp.databinding.FragmentDescriptionNewsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DescriptionNewsFragment : Fragment() {

    lateinit var binding: FragmentDescriptionNewsBinding
    private val saveArgs: DescriptionNewsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDescriptionNewsBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        val viewModel = (activity as MainActivity).viewModel
        val article = saveArgs.article
        log(article.toString())
        val supportActionBar = (activity as MainActivity?)?.supportActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)




            binding.webView.apply {

                webViewClient = WebViewClient()

                lifecycleScope.launch{
                    binding.apply {
                        webView.loadUrl(article.url!!)
                        webView.visibility = View.GONE
                        floatingActionButton.visibility = View.GONE
                        delay(2000)
                        webView.visibility = View.VISIBLE
                        floatingActionButton.visibility = View.VISIBLE
                        progressBar2.visibility = View.GONE
                    }
                }
            }

        binding.floatingActionButton.setOnClickListener {
            viewModel.saveNews(article)
        }
    }
}