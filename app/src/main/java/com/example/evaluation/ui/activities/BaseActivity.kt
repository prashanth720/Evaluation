package com.example.evaluation.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.evaluation.utils.NavigationController

abstract class BaseActivity : AppCompatActivity() {
    var navigationController: NavigationController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init(){
        navigationController = NavigationController(this, supportFragmentManager)
    }
}