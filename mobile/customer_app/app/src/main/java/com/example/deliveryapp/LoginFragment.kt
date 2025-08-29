package com.example.deliveryapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.deliveryapp.data.model.LoginRequest
import com.example.deliveryapp.data.network.RetrofitInstance
import com.example.deliveryapp.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch
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
                        val loginResponse = response.body()!!
                        Log.d("LoginFragment", "Login Success: ${loginResponse.name}, Token: ${loginResponse.token}")
                        Toast.makeText(context, "Welcome, ${loginResponse.name}!", Toast.LENGTH_SHORT).show()

                        // --- 화면 전환 로직 추가 ---
                        // 로그인 성공 후 메인 화면으로 이동
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