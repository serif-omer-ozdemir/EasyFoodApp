package com.example.foodapp.retrofit

import com.example.foodapp.data.entity.Meal
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitInstange {

    //https://www.themealdb.com/api/json/v1/1/random.php

    val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

    val api: MealDao by lazy {
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(MealDao::class.java)
    }


}