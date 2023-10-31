package com.example.foodapp.ui.fragment.bottomsheet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.FragmentMealBottomSheetBinding
import com.example.foodapp.ui.activity.MainActivity
import com.example.foodapp.ui.activity.MeanDetailsActivity
import com.example.foodapp.ui.viewmodel.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

private const val MEAL_ID = "param1"

class mealBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentMealBottomSheetBinding
    private lateinit var vm: HomeViewModel

    private var mealId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(MEAL_ID)
        }

        vm = (activity as MainActivity).vm

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMealBottomSheetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.getMealById(mealId!!)
        observeBottomSheetMeal()
        //tıklanma işlemınde verı gonderılecek ve sayfalar arası gecıs olacak
        onBottomSheetclick()
    }

    private fun onBottomSheetclick() {
        //mealdetail actıvıty e verı goneder

        binding.bottomSheet.setOnClickListener {
            if (MEAL_NAME != null && MEAL_THUMB != null) {
                var intent = Intent(activity, MeanDetailsActivity::class.java)
                intent.putExtra("MEAL_ID", mealId)
                intent.putExtra("MEAL_NAME", MEAL_NAME)
                intent.putExtra("MEAL_THUMB", MEAL_THUMB)

                startActivity(intent)
            }
        }
    }

    private var MEAL_NAME: String? = null
    private var MEAL_THUMB: String? = null
    private fun observeBottomSheetMeal() {
        vm.observeBottomSheetMeal().observe(viewLifecycleOwner, Observer { meal ->
            Glide.with(this@mealBottomSheetFragment).load(meal.strMealThumb)
                .into(binding.imgBottomSheet)
            binding.apply {
                tvBottomSheetMealName.text = meal.strMeal
                tvCategoryBotoomSheet.text = meal.strCategory
                tvLocal.text = meal.strArea
            }
            //diger sinifa gonderılecek
            MEAL_NAME = meal.strMeal
            MEAL_THUMB = meal.strMealThumb
        })
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            mealBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID, param1)

                }
            }
    }
}