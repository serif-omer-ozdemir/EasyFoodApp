package com.example.foodapp.ui.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.foodapp.data.entity.Meal
import com.example.foodapp.databinding.ActivityMeanDetailsBinding
import com.example.foodapp.room.MealDatabase
import com.example.foodapp.ui.viewmodel.MealDetailsViewModel
import com.example.foodapp.ui.viewmodel.MealViewModelFactory

class MeanDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMeanDetailsBinding
    private lateinit var vm: MealDetailsViewModel

    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var mealYoutubeLink: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMeanDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mealDatabase = MealDatabase.getInstange(this)
        val viewModelFactory = MealViewModelFactory(mealDatabase)
        vm = ViewModelProvider(
            this,
            viewModelFactory
        ).get(MealDetailsViewModel::class.java) //erişim saglandı


        loadingCase()
        getMealInformationFromIntent()
        setInformationInViews()
        vm.getMealDetails(mealId)
        observeRandomMealDetails()
        onyoutubeImageClick()
        onFavoriteClick()

    }

    private fun onFavoriteClick() {
        binding.favButton.setOnClickListener {
            mealToSave?.let { //null degilse izin ver
                vm.insertMeal(it) //mealToSave = meal
                Toast.makeText(applicationContext, "Added To Favorites", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun onyoutubeImageClick() {
        binding.imgYoutube.setOnClickListener {
            //youtube app e gıt
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mealYoutubeLink))
            startActivity(intent)
        }
    }

    var mealToSave: Meal? = null//favarileri eklemek için yaptım
    private fun observeRandomMealDetails() {
        //gozlemle degıstıgınde tetıklen

        vm.observeRandomMealDetailsLiveData().observe(this, { meall ->

            responseCase()

            mealToSave = meall //favorilere eklemkiçin veriyi cektim

            mealId = meall.idMeal
            binding.colepsingToolbar.title = meall.strMeal
            binding.textViewInstructionsDetail.text = meall.strInstructions
            binding.textViewCategory.text = meall.strCategory
            binding.textViewArea.text = meall.strArea
            mealYoutubeLink = meall.strYoutube!!

        })
    }

    private fun setInformationInViews() {
        Glide.with(this@MeanDetailsActivity).load(mealThumb).into(binding.imgDetailsMeal)
        binding.colepsingToolbar.title = mealName
        binding.toolbarDetails.title =
            mealName //buna atamaya gerek yok zaten volepsing tool bar ataama yaptı

    }

    //gelen verileri al
    private fun getMealInformationFromIntent() {
        mealId = intent.getStringExtra("MEAL_ID")!!
        mealName = intent.getStringExtra("MEAL_NAME")!!
        mealThumb = intent.getStringExtra("MEAL_THUMB")!!

        Log.e("meal Id", mealId)
    }


    private fun loadingCase() { //yuklenırken
        binding.progresBar.visibility = View.VISIBLE
        binding.favButton.visibility = View.INVISIBLE
        binding.textViewInstructionsDetail.visibility = View.INVISIBLE
        binding.textViewArea.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE
        binding.textViewCategory.visibility = View.INVISIBLE
        binding.textViewInstructions.visibility = View.INVISIBLE
    }

    private fun responseCase() { //cevap geldiğinde
        binding.progresBar.visibility = View.INVISIBLE
        binding.favButton.visibility = View.VISIBLE
        binding.textViewInstructionsDetail.visibility = View.VISIBLE
        binding.textViewArea.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE
        binding.textViewCategory.visibility = View.VISIBLE
        binding.textViewInstructions.visibility = View.VISIBLE
    }
}