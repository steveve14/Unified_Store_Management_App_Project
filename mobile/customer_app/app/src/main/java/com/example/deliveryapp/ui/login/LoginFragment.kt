package com.example.deliveryapp.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.deliveryapp.data.local.SessionManager
import com.example.deliveryapp.data.model.LoginRequest
import com.example.deliveryapp.data.network.RetrofitInstance
import com.example.deliveryapp.databinding.FragmentLoginBinding
import com.example.deliveryapp.ui.auth.AuthActivity
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

            // 서버 연동 로직
            lifecycleScope.launch {
                try {
                    val request = LoginRequest(email, password)
                    val response = RetrofitInstance.api.login(request)

                    if (response.isSuccessful && response.body() != null) {
                        // ★★★★★ apiResponse 변수를 제거하고 response.body()를 직접 사용 ★★★★★
                        val loginData = response.body()!! // 이제 loginData가 바로 LoginResponse 객체

                        // 세션 저장
                        SessionManager.saveAuthToken(requireContext(), loginData.token)
                        SessionManager.saveUserInfo(requireContext(), loginData.name, email)

                        Log.d("LoginFragment", "Login Success: ${loginData.name}, Token: ${loginData.token}")
                        Toast.makeText(context, "Welcome, ${loginData.name}!", Toast.LENGTH_SHORT).show()

                        // 메인 화면으로 이동
                        (activity as? AuthActivity)?.navigateToMain()
                    } else {
                        // ★★★★★ 실패 시에도 .message가 없으므로 직접 메시지 처리 ★★★★★
                        val errorBody = response.errorBody()?.string() ?: "Login failed"
                        Log.e("LoginFragment", "Login Failed: $errorBody")
                        Toast.makeText(context, errorBody, Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    Log.e("LoginFragment", "Login Exception", e)
                    Toast.makeText(context, "An error occurred: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

