package com.example.deliveryapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.deliveryapp.databinding.ActivityAuthBinding
import com.google.android.material.tabs.TabLayoutMediator

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewPagerWithTabs()
    }

    private fun setupViewPagerWithTabs() {
        val pagerAdapter = AuthPagerAdapter(this)
        binding.viewPager.adapter = pagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Login"
                1 -> "Sign Up"
                else -> throw IllegalStateException("Invalid position: $position")
            }
        }.attach()
    }

    fun switchToLoginTab() {
        binding.viewPager.currentItem = 0 // 0번 인덱스인 Login 탭으로 전환
    }

    fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // AuthActivity를 종료하여 뒤로 가기로 돌아오지 못하게 함
    }
}