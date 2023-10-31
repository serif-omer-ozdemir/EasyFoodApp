package com.example.foodapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.data.entity.MealsByCategory
import com.example.foodapp.databinding.PopulerItemBinding

class MostPopulerAdapter :
    RecyclerView.Adapter<MostPopulerAdapter.cartViewItem>() {

     var onLongPresItemClickData: ((MealsByCategory) -> Unit)? = null
    lateinit var onClickItemData: ((MealsByCategory) -> Unit)

    private var myList = ArrayList<MealsByCategory>()


    fun setMeals(myList: ArrayList<MealsByCategory>) {
        this.myList = myList
        notifyDataSetChanged()//ilk verının glmesı için gunceller
    }


    inner class cartViewItem(var binding: PopulerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): cartViewItem {
        //hangı cart tasarıma baglanıcak
        val lyInf = LayoutInflater.from(parent.context)
        val y = PopulerItemBinding.inflate(lyInf, parent, false)
        return cartViewItem(y)
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    override fun onBindViewHolder(holder: cartViewItem, position: Int) {


        Glide.with(holder.itemView).load(myList[position].strMealThumb)
            .into(holder.binding.imgPopulerItem)

        holder.itemView.setOnLongClickListener {
            onLongPresItemClickData?.invoke(myList[position])
            true
        }


        holder.itemView.setOnClickListener {
            onClickItemData.invoke(myList[position])
        }

    }
}