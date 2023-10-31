package com.example.foodapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.data.entity.Category
import com.example.foodapp.data.entity.MealsByCategory
import com.example.foodapp.data.entity.Meal
import com.example.foodapp.databinding.FragmentHomeBinding
import com.example.foodapp.ui.activity.CategoryMealsActivity
import com.example.foodapp.ui.activity.MainActivity
import com.example.foodapp.ui.activity.MeanDetailsActivity
import com.example.foodapp.ui.adapter.CategoriesAdapter
import com.example.foodapp.ui.adapter.MostPopulerAdapter
import com.example.foodapp.ui.fragment.bottomsheet.mealBottomSheetFragment
import com.example.foodapp.ui.viewmodel.HomeViewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var vm: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var populerItemAdapter: MostPopulerAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter

    companion object {
        //aslinda bunlara gerek yok
        // const val MEAL_ID = "com.example.foodapp.data.entity.Meal.idMeal" //yemek id
        // const val MEAL_NAME = "com.example.foodapp.data.entity.Meal.strMeal" //yemek ad  strMeal
        // const val MEAL_THUMB = "com.example.foodapp.data.entity.Meal.strMealThumb" //yemekk resim  strMealThumb
        // const val CATEGORY_NAME = "com.example.foodapp.data.entity.Meal.strCategory"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //bunu yapmamın sebebı viewmodeldeki parametreyi kullanmak bunn
        // içi viewmodel providers kullanıldı onuda main activityde tanımladıgım için oradan erıstım
        //viewmodel providerinn tek olayı viewmodel içinde parametre almak

        vm = (activity as MainActivity).vm //main actıvıtıydekı view modelle aynı

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopulerItemRv()

        vm.getRandomMeal() //veri atayarak live data tetıklendı
        observeRandomMeal() //ve gorsel yuklendi
        onRandomMealClick()


        vm.getPopulerItems()
        observePopulerItemsLiveData()
        onPopulerItemClick()


        vm.getCategories()
        observeCategoriesItemLiveData()
        prepareCategoryItemRv()
        onCategoryClick()

        onLongPressItemClick()

        onSearchItemClick()


    }

    private fun onSearchItemClick() {
        binding.imgSearch.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.homeFrToSearchFr)
        }
    }

    private fun onLongPressItemClick() {
        populerItemAdapter.onLongPresItemClickData = { meal ->
            // bottomshet fragmantla baglantı saglandı
            var mealBtmShtFragmet = mealBottomSheetFragment.newInstance(meal.idMeal)
            mealBtmShtFragmet.show(childFragmentManager, "Meal Infoo")
        }

    }

    private fun onCategoryClick() {
        //herhangiibir kategorıye tıkaldıgımda su verilerı gonder
        categoriesAdapter.onClickItem = { meals ->
            var intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra("CATEGORY_NAME", meals.strCategory)
            intent.putExtra("MEAL_ID", meals.idCategory)
            intent.putExtra("MEAL_NAME", meals.strCategory)
            intent.putExtra("MEAL_THUMB", meals.strCategoryThumb)
            startActivity(intent)
        }
    }

    private fun prepareCategoryItemRv() {
        binding.recViewCategory.apply {
            categoriesAdapter = CategoriesAdapter()
            setHasFixedSize(true)
            layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            adapter = categoriesAdapter
        }

    }


    private fun observeCategoriesItemLiveData() {
        vm.observeCategoryMealLiveData().observe(viewLifecycleOwner)
        { ctgrList ->
            categoriesAdapter.setCategories(myCategoryList = ctgrList as ArrayList<Category>)//list to ArrayList
            Log.e("observe categories", ctgrList[1].idCategory)

        }
    }

    private fun onPopulerItemClick() {
        populerItemAdapter.onClickItemData = { meal ->
            var intent = Intent(activity, MeanDetailsActivity::class.java)
            intent.putExtra("MEAL_ID", meal.idMeal)
            intent.putExtra("MEAL_NAME", meal.strMeal)
            intent.putExtra("MEAL_THUMB", meal.strMealThumb)
            startActivity(intent)
        }

    }

    private fun preparePopulerItemRv() {
        binding.recViewMealsPopular.apply {
            populerItemAdapter = MostPopulerAdapter()
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = populerItemAdapter
        }

    }

    private fun observePopulerItemsLiveData() {
        vm.observePopulerMealLiveData()
            .observe(
                viewLifecycleOwner
            ) { mealList ->
                populerItemAdapter.setMeals(myList = mealList as ArrayList<MealsByCategory>)//list to ArrayList
            }
    }

    private fun getPopulerCategory() {
        vm.getPopulerItems()
    }

    private fun onRandomMealClick() {
        binding.imgRandomMeal.setOnClickListener {
            val intentt = Intent(requireContext(), MeanDetailsActivity::class.java)
            intentt.putExtra("MEAL_ID", randomMeal.idMeal)
            intentt.putExtra("MEAL_NAME", randomMeal.strMeal)
            intentt.putExtra("MEAL_THUMB", randomMeal.strMealThumb)
            startActivity(intentt)
        }

    }

    private fun observeRandomMeal() {
        vm.observeRandomMealLiveData().observe(viewLifecycleOwner, { meall ->
            Glide.with(this@HomeFragment).load(meall.strMealThumb).into(binding.imgRandomMeal)
            randomMeal =
                meall //yemekle ilgili gelen verileri değişkene  aktardık bunuda  dıger sayfaya gondercegız
        })
    }

}