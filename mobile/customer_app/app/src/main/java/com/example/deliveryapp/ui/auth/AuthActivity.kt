package com.example.deliveryapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.deliveryapp.data.local.SessionManager
import com.example.deliveryapp.data.model.LoginRequest
import com.example.deliveryapp.data.network.RetrofitInstance
import com.example.deliveryapp.databinding.ActivityAuthBinding
import com.example.deliveryapp.databinding.FragmentLoginBinding
import com.example.deliveryapp.ui.login.AuthPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

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

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "Please enter email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // --- 서버 연동 로직 시작 ---
            lifecycleScope.launch {
                try {
                    val request = LoginRequest(email, password)
                    val response = RetrofitInstance.api.login(request)

                    if (response.isSuccessful && response.body() != null) {
                        val loginData = response.body()!!.data!!

                        // --- 세션 저장 ---
                        SessionManager.saveAuthToken(requireContext(), loginData.token)
                        SessionManager.saveUserInfo(requireContext(), loginData.name, email)

                        Log.d("LoginFragment", "Login Success: ${loginData.name}, Token: ${loginData.token}")
                        Toast.makeText(context, "Welcome, ${loginData.name}!", Toast.LENGTH_SHORT).show()
                        (activity as? AuthActivity)?.navigateToMain()
                    } else {
                        val errorBody = response.errorBody()?.string() ?: "Unknown error"
                        Log.e("LoginFragment", "Login Failed: $errorBody")
                        Toast.makeText(context, "Login failed: $errorBody", Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    Log.e("LoginFragment", "Login Exception", e)
                    Toast.makeText(context, "An error occurred: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
            // --- 서버 연동 로직 끝 ---
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}