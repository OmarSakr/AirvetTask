package com.codevalley.airvettask.main.activities.userDetails.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.codevalley.airvettask.R
import com.codevalley.airvettask.databinding.ActivityHomeBinding
import com.codevalley.airvettask.databinding.ActivityUserDetailsBinding
import com.codevalley.airvettask.models.Result
import com.codevalley.airvettask.utils.ParentClass

class UserDetailsActivity : ParentClass() {

    private lateinit var binding: ActivityUserDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        initUi()
    }

    private fun initUi() {
        val userData = intent.getParcelableExtra<Result>("userData")
        loadImageWithPicasso(userData?.picture?.large, this,binding.ivUserImage)
        binding.tvName.text = userData?.name?.first
        binding.tvMobile.text = userData?.phone
        binding.tvLocation.text = userData?.location?.country
        binding.tvCity.text = userData?.location?.city
        binding.tvEmail.text = userData?.email
        binding.tvGender.text = userData?.gender
    }
}