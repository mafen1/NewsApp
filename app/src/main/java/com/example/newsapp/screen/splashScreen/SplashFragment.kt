package com.example.newsapp.screen.splashScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentListNewsBinding
import com.example.newsapp.databinding.FragmentSplashBinding
import com.example.newsapp.screen.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    override fun initView() {
        activity?.lifecycleScope?.launch(Dispatchers.Main) {
            delay(2000)
            findNavController().navigate(R.id.action_splashFragment_to_listNewsFragment)
        }

    }
}