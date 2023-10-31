package com.example.foodapp.ui.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.foodapp.R
import com.example.foodapp.databinding.ActivityMainBinding
import com.example.foodapp.room.MealDatabase
import com.example.foodapp.ui.viewmodel.HomeViewModel
import com.example.foodapp.ui.viewmodel.HomeViewModelFactory
import com.example.foodapp.ui.viewmodel.MealDetailsViewModel

class MainActivity : AppCompatActivity() {
    val vm: HomeViewModel by lazy {
        val mealDatabase = MealDatabase.getInstange(this)
        val homeViewModelProviderFactory = HomeViewModelFactory(mealDatabase)
        ViewModelProvider(
            this,
            homeViewModelProviderFactory
        )[HomeViewModel::class.java] 
    }


    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragmentIm) as NavHostFragment
        NavigationUI.setupWithNavController(
            binding.btmNavigationViewIm,
            navHostFragment.navController
        )



    }


}