package com.example.deliveryapp.ui.singup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.deliveryapp.data.model.SignUpRequest
import com.example.deliveryapp.data.network.RetrofitInstance
import com.example.deliveryapp.databinding.FragmentSignUpBinding
import com.example.deliveryapp.ui.auth.AuthActivity
import kotlinx.coroutines.launch

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignUp.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etConfirmPassword.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 서버 연동 로직
            lifecycleScope.launch {
                try {
                    val request = SignUpRequest(name, email, password)
                    val response = RetrofitInstance.api.signUp(request)

                    if (response.isSuccessful) {
                        Log.d("SignUpFragment", "Sign Up Success")
                        Toast.makeText(context, "Sign up successful! Please log in.", Toast.LENGTH_LONG).show()

                        // 로그인 탭으로 전환
                        (activity as? AuthActivity)?.switchToLoginTab()
                    } else {
                        val errorBody = response.errorBody()?.string() ?: "Unknown error"
                        Log.e("SignUpFragment", "Sign Up Failed: $errorBody")
                        Toast.makeText(context, "Sign up failed: $errorBody", Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    Log.e("SignUpFragment", "Sign Up Exception", e)
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