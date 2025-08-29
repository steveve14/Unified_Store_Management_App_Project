package com.example.deliveryapp.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.deliveryapp.ui.orders.OrderListFragment

// OrdersFragment 내에서 ViewPager를 제어하는 어댑터
class OrdersPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    // 탭은 'Current Orders'와 'Past Orders' 두 개입니다.
    override fun getItemCount(): Int = 2

    // 각 포지션(탭)에 어떤 프래그먼트를 보여줄지 결정합니다.
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            // 0번 탭(Current)을 클릭하면 isCurrentOrders = true로 OrderListFragment 생성
            0 -> OrderListFragment.newInstance(isCurrentOrders = true)
            // 1번 탭(Past)을 클릭하면 isCurrentOrders = false로 OrderListFragment 생성
            1 -> OrderListFragment.newInstance(isCurrentOrders = false)
            else -> throw IllegalStateException("Invalid position for Orders Pager")
        }
    }
}