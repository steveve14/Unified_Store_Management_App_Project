package com.example.deliveryapp.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.deliveryapp.MainActivity
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

    // SignUpFragment에서 호출할 함수
    fun switchToLoginTab() {
        binding.viewPager.currentItem = 0
    }

    // LoginFragment에서 호출할 함수
    fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        // 이전의 모든 액티비티를 스택에서 제거하고 새로운 태스크를 시작
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish() // AuthActivity 종료
    }
}