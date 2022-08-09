package com.example.newsapp.screen.descriptionNews

import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.newsapp.databinding.FragmentDescriptionNewsBinding
import com.example.newsapp.screen.BaseFragment
import com.example.newsapp.screen.MainActivity
import com.example.newsapp.screen.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DescriptionNewsFragment :
    BaseFragment<FragmentDescriptionNewsBinding>(FragmentDescriptionNewsBinding::inflate) {

    private val saveArgs: DescriptionNewsFragmentArgs by navArgs()
    private val viewModel by viewModels<MainViewModel>()

    override fun initView() = with(binding){
        val article = saveArgs.article

        val supportActionBar = (activity as MainActivity?)?.supportActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        webView.apply {

            webViewClient = WebViewClient()

            lifecycleScope.launch {
                    webView.loadUrl(article.url!!)
                    webView.visibility = View.GONE
                    floatingActionButton.visibility = View.GONE
                    delay(2000)
                    webView.visibility = View.VISIBLE
                    floatingActionButton.visibility = View.VISIBLE
                    progressBar2.visibility = View.GONE
            }
        }

        floatingActionButton.setOnClickListener {
            viewModel.saveNews(article)
        }
    }
}