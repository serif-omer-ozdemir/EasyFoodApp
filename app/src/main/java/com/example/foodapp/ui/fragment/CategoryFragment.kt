package com.example.foodapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapp.R
import com.example.foodapp.data.entity.Category
import com.example.foodapp.databinding.FragmentCategoryBinding
import com.example.foodapp.ui.activity.CategoryMealsActivity
import com.example.foodapp.ui.activity.MainActivity
import com.example.foodapp.ui.adapter.CategoriesAdapter
import com.example.foodapp.ui.viewmodel.HomeViewModel


class CategoryFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var vm: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //bunu yapmamın sebebı viewmodeldeki parametreyi kullanmak bunn
        // içi viewmodel providers kullanıldı onuda main activityde tanımladıgım için oradan erıstım
        //viewmodel providerinn tek olayı viewmodel içinde parametre almak

        vm = (activity as MainActivity).vm
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCategoryBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyViewCategori()

        //vm.getCategories()  yazmama gerek yok zaten uygulama acılıs
        // ekranında hhome fragmentdaki kategori ile
        // aynı veri ben zaten onu cagırmıstım home viewmodelde
        //tekrar yazmaa gerek yok
        // vm.getCategories()


        observeGetCategory()
        onClickCategory()
    }

    private fun onClickCategory() {
        categoriesAdapter.onClickItem = { meals ->

            var intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra("CATEGORY_NAME", meals.strCategory)
            intent.putExtra("MEAL_ID", meals.idCategory)
            intent.putExtra("MEAL_NAME", meals.strCategory)
            intent.putExtra("MEAL_THUMB", meals.strCategoryThumb)
            startActivity(intent)


        }
    }

    private fun observeGetCategory() {
        vm.observeCategoryMealLiveData().observe(viewLifecycleOwner, Observer { categoriesList ->
            categoriesAdapter.setCategories(categoriesList as ArrayList<Category>)
        })
    }

    private fun prepareRecyViewCategori() {
        categoriesAdapter = CategoriesAdapter()

        binding.rvCtgryCategori.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }

    }


}