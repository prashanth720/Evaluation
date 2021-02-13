package com.example.evaluation.ui.activities

import android.os.Bundle
import com.example.evaluation.R
import com.example.evaluation.ui.adapters.PagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        launchHomeFragment()
    }

    private fun launchHomeFragment(){
        navigationController?.launchHomeFragment()
    }


}