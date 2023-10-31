package com.example.foodapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.data.entity.Category
import com.example.foodapp.data.entity.MealsByCategory
import com.example.foodapp.databinding.CategoryItemBinding
import com.example.foodapp.databinding.PopulerItemBinding

class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.CategoryCartItem>() {


    var myCategoryList = ArrayList<Category>()

    var onClickItem:((Category)-> Unit)? = null


    fun setCategories(myCategoryList: ArrayList<Category>) {
        this.myCategoryList = myCategoryList
        notifyDataSetChanged()//ilk verının glmesı için gunceller
    }

    inner class CategoryCartItem(var binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryCartItem {
        val lyInf = LayoutInflater.from(parent.context)
        val y = CategoryItemBinding.inflate(lyInf, parent, false)
        return CategoryCartItem(y)
    }

    override fun getItemCount(): Int {
        return myCategoryList.size
    }

    override fun onBindViewHolder(holder: CategoryCartItem, position: Int) {
        Glide.with(holder.itemView).load(myCategoryList[position].strCategoryThumb)
            .into(holder.binding.imgCategory)

        holder.binding.tvCategoryName.text = myCategoryList[position].strCategory

        holder.itemView.setOnClickListener {
            onClickItem!!.invoke(myCategoryList[position])
             }
    }
}