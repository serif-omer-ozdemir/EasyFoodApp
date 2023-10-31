package com.example.foodapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapp.R
import com.example.foodapp.databinding.FragmentCategoryBinding
import com.example.foodapp.databinding.FragmentSearchBinding
import com.example.foodapp.ui.activity.MainActivity
import com.example.foodapp.ui.adapter.SearchMeaAdapter
import com.example.foodapp.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var vm: HomeViewModel

    private lateinit var searchAdapter: SearchMeaAdapter

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

        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        preaparereRecyclerView()

        //arama butonna  tıklandıgında
        binding.imgArrow.setOnClickListener { searchMeals() }

        observeSearchResultLiveData()


        //ya da bız yazdıgımızda soncu gercek zamanlı versın
        onSearchTextChangeLivee()

    }

    private fun onSearchTextChangeLivee() {


       var searchJob: Job?=null //job coroutine sınıfından
        binding.searchBox.addTextChangedListener { searchQuery->
            searchJob?.cancel()
            searchJob=lifecycleScope.launch {
                delay(500)//500 milisaniye bekle
                vm.getSearchResulst(searchQuery.toString())
            }

        }
    }

    private fun observeSearchResultLiveData() {
        vm.observeSearchMealList().observe(viewLifecycleOwner, Observer {mealList->
            for (x in mealList) {
                searchAdapter.differ.submitList(mealList)
                Log.e("favorite meal name", x.strMeal!!)
            }
        })
    }

    private fun searchMeals() {
        val searchQuery=binding.searchBox.text.toString()
        if (searchQuery.isNotEmpty()){
            vm.getSearchResulst(searchQuery)
        }

    }

    private fun preaparereRecyclerView() {
        searchAdapter = SearchMeaAdapter()
        binding.rvSearch.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = searchAdapter
        }
    }

}