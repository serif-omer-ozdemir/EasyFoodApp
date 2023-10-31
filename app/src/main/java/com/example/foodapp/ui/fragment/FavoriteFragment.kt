package com.example.foodapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.foodapp.R
import com.example.foodapp.databinding.FragmentFavoriteBinding
import com.example.foodapp.ui.activity.MainActivity
import com.example.foodapp.ui.adapter.FavoriteAdapter
import com.example.foodapp.ui.viewmodel.HomeViewModel
import com.google.android.material.snackbar.Snackbar


class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var vm: HomeViewModel
    private lateinit var favAdapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //bunu yapmamın sebebı viewmodeldeki parametreyi kullanmak bunn
        // içi viewmodel providers kullanıldı onuda main activityde tanımladıgım için oradan erıstım
        //viewmodel providerinn tek olayı viewmodel içinde parametre almak

        vm = (activity as MainActivity).vm
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        prepareFavoriteRecyView()
        observeFavorites()
        setListsFavorites()

    }

    private fun setListsFavorites() {
        //saga kaydırınca sılmeyı saglar
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.DOWN or ItemTouchHelper.UP,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT //silme yonu
        ) {
            override fun onMove( //tasşindiğinda bir eylem olması
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            //kaydırıldıgında silinsin
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val position = viewHolder.adapterPosition
                val deletedMeal = favAdapter.differ.currentList[position]
                vm.deleteMeal(deletedMeal)

                Snackbar.make(requireView(), "Meal Deleted", Snackbar.LENGTH_LONG).setAction(
                    "Undo",
                    View.OnClickListener {
                        vm.insertMeal(deletedMeal)
                    }
                ).show()

            }

        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavorite)
    }


    private fun prepareFavoriteRecyView() {
        favAdapter = FavoriteAdapter()

        binding.rvFavorite.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = favAdapter
        }

    }

    private fun observeFavorites() {
        vm.observeFavoriteLiveData().observe(viewLifecycleOwner, Observer { mealList ->
            for (x in mealList) {
                favAdapter.differ.submitList(mealList)
                Log.e("favorite meal name", x.strMeal!!)
            }

        })
    }

}