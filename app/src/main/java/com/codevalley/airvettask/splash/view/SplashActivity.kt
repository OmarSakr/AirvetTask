package com.codevalley.airvettask.splash.view

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.codevalley.airvettask.R
import com.codevalley.airvettask.main.activities.home.view.HomeActivity
import com.codevalley.airvettask.splash.viewModel.SplashViewModel
import com.codevalley.airvettask.utils.ParentClass
import kotlinx.coroutines.flow.collect

class SplashActivity : ParentClass() {
    private lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUi()

    }

    private fun initUi() {
        splashViewModel = ViewModelProvider(this).get(SplashViewModel::class.java)
        splashViewModel.startTimer()
    }


    private fun go() {
        lifecycleScope.launchWhenStarted {
            splashViewModel.mutableStateFlow.collect {
                val intent = Intent(this@SplashActivity, HomeActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                startActivity(intent)
            }

        }

    }

    override fun onResume() {
        super.onResume()
        go()
        setDefaultLang(getLang(this), this)
    }
}