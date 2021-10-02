package com.codevalley.airvettask.main.activities.home.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codevalley.airvettask.databinding.ActivityHomeBinding
import com.codevalley.airvettask.main.activities.home.viewModel.HomeViewModel
import com.codevalley.airvettask.main.activities.home.viewModel.HomeViewModelFactory
import com.codevalley.airvettask.main.adapters.CacheAdapter
import com.codevalley.airvettask.main.adapters.HomeAdapter
import com.codevalley.airvettask.utils.AppController
import com.codevalley.airvettask.utils.ParentClass
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch

class HomeActivity : ParentClass() {
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var cacheAdapter: CacheAdapter
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
        homeViewModel = ViewModelProvider(
            this, HomeViewModelFactory((application as AppController).repository)
        ).get(HomeViewModel::class.java)
        if (checkForInternet(this@HomeActivity)) {
            homeViewModel.deleteAllUsers()
            getUsers()
            initHomeRecycler()
            checkLoadingState()
            addUsersToCache()
        } else {

            getUsersFromCache()
            initCacheRecycler()
        }
    }

    private fun addUsersToCache() {
        lifecycleScope.launch {
            //Your adapter's loadStateFlow here
            homeAdapter.loadStateFlow.distinctUntilChangedBy {
                it.refresh
            }.collect {

                //you get all the data here
                if (homeAdapter.snapshot().items.isNotEmpty()) {
                    homeViewModel.addUsers(homeAdapter.snapshot().items)
                }

            }
        }
    }

    private fun initCacheRecycler() {
        cacheAdapter = CacheAdapter(this)
        val linearLayoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvHome.layoutManager = linearLayoutManager
        binding.rvHome.adapter = cacheAdapter
    }


    private fun getUsersFromCache() {
        homeViewModel.allUsers.observe(this, {
            Log.e("ittt", it.toString())
            cacheAdapter.addAll(it)
            cacheAdapter.notifyDataSetChanged()
        })
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

    private fun initHomeRecycler() {
        //initializeHomeAdapter
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
            ).collectLatest { pagingData ->
                homeAdapter.submitData(pagingData)
            }
        }

    }
}