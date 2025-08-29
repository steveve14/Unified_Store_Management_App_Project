package com.example.deliveryapp.ui.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.deliveryapp.databinding.FragmentOrdersBinding
import com.example.deliveryapp.ui.adapters.OrdersPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class OrdersFragment : Fragment() {
    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pagerAdapter = OrdersPagerAdapter(this)
        binding.ordersViewPager.adapter = pagerAdapter

        TabLayoutMediator(binding.ordersTabLayout, binding.ordersViewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Current Orders"
                1 -> "Past Orders"
                else -> ""
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}