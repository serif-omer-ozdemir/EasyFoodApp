package com.example.foodapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.data.entity.Meal
import com.example.foodapp.data.entity.MealList
import com.example.foodapp.retrofit.RetrofitInstange
import com.example.foodapp.room.MealDatabase
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealDetailsViewModel(val mealDatabase: MealDatabase) : ViewModel() {

    var mealDetailList = MutableLiveData<Meal>()

    fun getMealDetails(id: String) {

        RetrofitInstange.api.getMealDetails(id).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {

                if (response.body() != null) {
                    val randomMeal: Meal = response.body()!!.meals[0]
                    mealDetailList.value = randomMeal
                    Log.e("yemek  ad ", randomMeal.strMeal!!)
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.e(
                    "hata oldu mealdetailsviewmodel da", t.message.toString()
                )
            }


        })

    }

    fun observeRandomMealDetailsLiveData(): LiveData<Meal> {
        return mealDetailList
    }

    fun insertMeal(meal: Meal) {
        viewModelScope.launch {
            mealDatabase.getMealsDao().insertMeal(meal)
        }
    }



}