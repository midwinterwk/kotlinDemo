package com.dimsum.jetpack.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.dimsum.jetpack.R

class NavMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_main)
//        Navigation.findNavController(this, R.id.fragment).let {
//            NavigationUI.setupActionBarWithNavController(this, it)
//        }
    }

//    override fun onSupportNavigateUp(): Boolean {
//        return Navigation.findNavController(this, R.id.fragment).navigateUp()
//    }
}