package com.example.foodapp.retrofit

import androidx.room.Dao
import com.example.foodapp.data.entity.CategoryList
import com.example.foodapp.data.entity.MealsByCategoryList
import com.example.foodapp.data.entity.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface MealDao {
    //https://www.themealdb.com/api/json/v1/1/random.php
   // www.themealdb.com/api/json/v1/1/search.php?s=Arrabiata

    @GET("random.php")
     fun getRandomMeal(): retrofit2.Call<MealList>

     @GET("lookup.php")   //soru işareti olanlarda sorgulama yaoılır
     fun getMealDetails(@Query("i") id:String):retrofit2.Call<MealList>


    @GET("filter.php")
    fun getPopulerMeal(@Query("c") categoryName:String):retrofit2.Call<MealsByCategoryList>

    @GET("categories.php")
    fun getCategories():Call<CategoryList>

    @GET("filter.php")
    fun getMealsByCategory(@Query("c") categoryName:String):Call<MealsByCategoryList>

    @GET("search.php")
    fun getSearchMealList(@Query("s") keyword:String):Call<MealList>

}