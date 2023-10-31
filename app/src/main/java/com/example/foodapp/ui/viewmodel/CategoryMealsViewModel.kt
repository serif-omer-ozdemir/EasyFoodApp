package com.example.foodapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.data.entity.MealsByCategory
import com.example.foodapp.data.entity.MealsByCategoryList
import com.example.foodapp.retrofit.RetrofitInstange
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModel : ViewModel() {

    var listMealLiveData = MutableLiveData<List<MealsByCategory>>()

    fun getMealsByCategory(categoryName: String) {
        RetrofitInstange.api.getMealsByCategory(categoryName)
            .enqueue(object : Callback<MealsByCategoryList> {
                override fun onResponse(
                    call: Call<MealsByCategoryList>,
                    response: Response<MealsByCategoryList>
                ) {
                    if (response.body() != null) {
                        listMealLiveData.value =
                            response.body()!!.meals//rasgele gelen ilk ındex i al
                    } else {
                        return
                    }
                }

                override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                    Log.e("hata olsuttu categorymealsviewsmodelde", t.message.toString())
                }

            })
    }

    fun observeCategoryMeals(): LiveData<List<MealsByCategory>> {
        return listMealLiveData
    }


}