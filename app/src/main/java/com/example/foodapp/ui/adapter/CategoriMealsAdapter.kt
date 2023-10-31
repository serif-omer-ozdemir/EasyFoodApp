package com.example.foodapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.data.entity.MealsByCategory
import com.example.foodapp.databinding.MealItemBinding

class CategoriMealsAdapter : RecyclerView.Adapter<CategoriMealsAdapter.CatergoryMealsItem>() {

    var myList = ArrayList<MealsByCategory>()

    fun setMeals(myList:List<MealsByCategory>) {
        this.myList = myList as ArrayList<MealsByCategory>
        notifyDataSetChanged() // unutmaa
    }


    inner class CatergoryMealsItem(var binding: MealItemBinding) : RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatergoryMealsItem {
        val lyInf = LayoutInflater.from(parent.context)
        val y = MealItemBinding.inflate(lyInf, parent, false)
        return CatergoryMealsItem(y)
    }

    override fun getItemCount(): Int {
       return myList.size
    }

    override fun onBindViewHolder(holder: CatergoryMealsItem, position: Int) {
        Glide.with(holder.itemView).load(myList[position].strMealThumb)
            .into(holder.binding.imgViewMeal)

        holder.binding.tvMealName.text=myList[position].strMeal

    }
}