package com.example.deliveryapp.ui.auth

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.deliveryapp.ui.singup.SignUpFragment
import com.example.deliveryapp.ui.login.LoginFragment

class AuthPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> LoginFragment()
            1 -> SignUpFragment()
            else -> throw IllegalStateException("Invalid position for Auth Pager: $position")
        }
    }
}