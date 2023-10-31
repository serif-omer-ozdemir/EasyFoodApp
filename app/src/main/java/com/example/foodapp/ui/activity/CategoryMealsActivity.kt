package com.example.foodapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.data.entity.MealsByCategory
import com.example.foodapp.databinding.ActivityCategoryMealsBinding
import com.example.foodapp.databinding.ActivityMeanDetailsBinding
import com.example.foodapp.ui.adapter.CategoriMealsAdapter
import com.example.foodapp.ui.adapter.CategoriesAdapter
import com.example.foodapp.ui.fragment.HomeFragment
import com.example.foodapp.ui.viewmodel.CategoryMealsViewModel
import com.example.foodapp.ui.viewmodel.MealDetailsViewModel

class CategoryMealsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryMealsBinding
    private lateinit var vm: CategoryMealsViewModel

    private lateinit var category: String
    private lateinit var mealAdapter:CategoriMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        vm = ViewModelProvider(this).get(CategoryMealsViewModel::class.java) //erişim saglandı


        prepareCategoryItemRv()

        getMealInformationFromIntent()
        observeCategoryMeals()

    }
    private fun prepareCategoryItemRv() {
        binding.rvMeals.apply {
            mealAdapter = CategoriMealsAdapter()
            setHasFixedSize(true)
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = mealAdapter
        }

    }

    private fun observeCategoryMeals() {
        vm.observeCategoryMeals().observe(this, { meals ->
            for (mealx in meals) {
                Log.e("testt", mealx.strMeal)
                var ctgr=intent.getStringExtra("CATEGORY_NAME")!!
                binding.tvCategoryCount.text="$ctgr : ${meals.size}"//kategori ad ve yemek sayısı
            }
            mealAdapter.setMeals(meals)
        })
    }

    private fun getMealInformationFromIntent() {
        Log.e("category Id", "")
        category = intent.getStringExtra("CATEGORY_NAME")!!
        vm.getMealsByCategory(category)

    }


}