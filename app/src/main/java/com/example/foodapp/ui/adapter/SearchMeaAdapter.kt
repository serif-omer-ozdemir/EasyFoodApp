package com.example.foodapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.data.entity.Meal
import com.example.foodapp.databinding.FovoriteItemBinding

class SearchMeaAdapter : RecyclerView.Adapter<SearchMeaAdapter.CartFavoriItem>() {

    inner class CartFavoriItem(var binding: FovoriteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {}




    //notifaydatachanget yerıne geldı  değişim oldu
    private val diffUtil = object : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal //true yada false doner
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }

    }

    var differ = AsyncListDiffer(this, diffUtil)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartFavoriItem {
        val lyInf = LayoutInflater.from(parent.context)
        val y = FovoriteItemBinding.inflate(lyInf, parent, false)
        return CartFavoriItem(y)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CartFavoriItem, position: Int) {
        val mealList=differ.currentList[position]

        Glide.with(holder.itemView).load(mealList.strMealThumb)
            .into(holder.binding.imgFavorite)

        holder.binding.tvFavorite.text=mealList.strMeal



    }
}