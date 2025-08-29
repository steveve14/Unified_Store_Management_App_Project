package com.example.deliveryapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deliveryapp.databinding.FragmentHomeBinding
import com.example.deliveryapp.ui.adapters.BannerAdapter
import com.example.deliveryapp.ui.adapters.CategoryAdapter
import com.example.deliveryapp.ui.adapters.RestaurantAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBannerViewPager()
        setupRecyclerViews()
        observeViewModel()
    }

    private fun setupBannerViewPager() {
        val dummyBanners = listOf("url1", "url2", "url3") // 실제로는 ViewModel에서
        binding.bannerViewPager.adapter = BannerAdapter(dummyBanners)
    }

    private fun setupRecyclerViews() {
        binding.categoryRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recommendedRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun observeViewModel() {
        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            binding.categoryRecyclerView.adapter = CategoryAdapter(categories)
        }
        viewModel.restaurants.observe(viewLifecycleOwner) { restaurants ->
            binding.recommendedRecyclerView.adapter = RestaurantAdapter(restaurants)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}