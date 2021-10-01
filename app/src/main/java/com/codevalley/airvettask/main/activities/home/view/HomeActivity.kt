package com.codevalley.airvettask.main.activities.home.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codevalley.airvettask.R
import com.codevalley.airvettask.databinding.ActivityHomeBinding
import com.codevalley.airvettask.main.activities.home.viewModel.HomeViewModel
import com.codevalley.airvettask.main.adapters.HomeAdapter
import kotlinx.coroutines.flow.collectLatest

class HomeActivity : AppCompatActivity() {
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeAdapter: HomeAdapter
    private var called = false

    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        initUi()
    }

    private fun initUi() {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        getUsers()
        initRecycler()
        checkLoadingState()
    }

    private fun checkLoadingState() {
        homeAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                binding.progressBar.visibility = View.VISIBLE
                called = true
            } else {
                if (called) {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun initRecycler() {
        homeAdapter = HomeAdapter(this)
        val linearLayoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvHome.layoutManager = linearLayoutManager
        binding.rvHome.adapter = homeAdapter


    }

    private fun getUsers() {
        binding.progressBar.visibility = View.VISIBLE
        lifecycleScope.launchWhenStarted {
            homeViewModel.getUsers(
            ).collectLatest {
                homeAdapter.submitData(it)
            }
        }

    }
}