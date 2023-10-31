package com.example.foodapp.ui.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.data.entity.Category
import com.example.foodapp.data.entity.CategoryList
import com.example.foodapp.data.entity.MealsByCategoryList
import com.example.foodapp.data.entity.MealsByCategory
import com.example.foodapp.data.entity.Meal
import com.example.foodapp.data.entity.MealList
import com.example.foodapp.retrofit.RetrofitInstange
import com.example.foodapp.room.MealDatabase
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(
    private val mealDatabase: MealDatabase
) : ViewModel() {

    var randomMealLiveData = MutableLiveData<Meal>()
    var populerMealLiveData = MutableLiveData<List<MealsByCategory>>()
    var categoryMealLiveData = MutableLiveData<List<Category>>()
    var favoriteMealLiveData = mealDatabase.getMealsDao().getAllmeals()
    var bottomSheetListLiveData = MutableLiveData<Meal>()
    var searchMealLiveData = MutableLiveData<List<Meal>>()




    fun getRandomMeal() {
        RetrofitInstange.api.getRandomMeal().enqueue(object : Callback<MealList> {
            //basarılı ondugunda yanıt:
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    val randomMeal: Meal = response.body()!!.meals[0]//rasgele gelen ilk ındex i al

                    randomMealLiveData.value = randomMeal

                    Log.e("yemek  id ", randomMeal.idMeal)
                } else {
                    return
                }
            }

            //hata durumunda
            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.e(
                    "hata oldu homeviewmodel getRandomMeal da", t.message.toString()
                )
            }

        })

    }

    fun getPopulerItems() {
        RetrofitInstange.api.getPopulerMeal("Seafood")
            .enqueue(object : Callback<MealsByCategoryList> {
                //basarılı ondugunda yanıt:
                override fun onResponse(
                    call: Call<MealsByCategoryList>,
                    response: Response<MealsByCategoryList>
                ) {
                    if (response.body() != null) {
                        populerMealLiveData.value =
                            response.body()!!.meals//rasgele gelen ilk ındex i al

                    } else {
                        return
                    }
                }

                //hata durumunda
                override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                    Log.e(
                        "hata oldu homeviewmodel getPopulerItems da", t.message.toString()
                    )
                }

            })
    }

    fun getCategories() {
        RetrofitInstange.api.getCategories().enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                if (response.body() != null) {
                    categoryMealLiveData.value =
                        response.body()!!.categories//rasgele gelen ilk ındex i al

                } else {
                    return
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.e(
                    "hata oldu homeviewmodel getCategories te", t.message.toString()
                )
            }

        })
    }

    fun getMealById(id: String) { //bottom sheet için
        RetrofitInstange.api.getMealDetails(id).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                var meals = response.body()?.meals?.first()// ilk elemana ıhtıyacım var
                if (response.body() != null) {
                    bottomSheetListLiveData.value = meals!!
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.e(
                    "hata oldu homeviewmodel  getMealById de", t.message.toString()
                )
            }

        })
    }

    fun getSearchResulst(keyword: String) {
        RetrofitInstange.api.getSearchMealList(keyword).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {

                if (response.body() != null) {
                    searchMealLiveData.value = response.body()!!.meals
                }else{
                    Log.e(
                        "hata oldu homeviewmodel  getSearchResulst da", " "
                    )
                }
            }
            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.e(
                    "hata oldu homeviewmodel  getSearchResulst da", t.message.toString()
                )
            }

        })
    }

    fun observeRandomMealLiveData(): LiveData<Meal> {
        return randomMealLiveData
    }

    fun observePopulerMealLiveData(): LiveData<List<MealsByCategory>> {
        return populerMealLiveData
    }

    fun observeCategoryMealLiveData(): LiveData<List<Category>> {
        return categoryMealLiveData
    }

    fun observeFavoriteLiveData(): LiveData<List<Meal>> {
        return favoriteMealLiveData
    }

    fun insertMeal(meal: Meal) {
        viewModelScope.launch {
            mealDatabase.getMealsDao().insertMeal(meal)
        }
    }

    fun deleteMeal(meal: Meal) {
        //viewmodel factory için
        viewModelScope.launch {
            mealDatabase.getMealsDao().deleteMeal(meal)
        }
    }

    fun observeBottomSheetMeal(): LiveData<Meal> {
        return bottomSheetListLiveData
    }

    fun observeSearchMealList(): LiveData<List<Meal>> {
        return searchMealLiveData
    }
}