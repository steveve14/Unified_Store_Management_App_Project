package com.example.deliveryapp // 본인의 패키지 이름 확인

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
// XML 파일 이름(activity_login)에 따라 자동 생성된 바인딩 클래스를 임포트합니다.
import com.example.deliveryapp.databinding.ActivityLoginBinding
import com.google.android.material.button.MaterialButton
import com.google.android.material.tabs.TabLayout

// 클래스 이름도 역할에 맞게 LoginActivity로 변경하는 것을 권장합니다.
class LoginActivity : AppCompatActivity() {

    // 뷰 바인딩 객체와 ViewModel 선언
    private lateinit var binding: ActivityLoginBinding // ★★★ ActivityLoginBinding으로 수정
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 뷰 바인딩 객체 초기화 및 화면 설정
        binding = ActivityLoginBinding.inflate(layoutInflater) // ★★★ ActivityLoginBinding으로 수정
        setContentView(binding.root)

        // 기능별 함수 호출
        setupTabs()
        setupValidationListeners()
        setupClickListeners()
        observeViewModel()
    }

    private fun setupTabs() {
        // 모든 뷰는 'binding' 객체를 통해 접근합니다.
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("로그인"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("회원가입"))

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val isLoginTab = tab?.position == 0
                val targetView = if (isLoginTab) binding.loginLayout else binding.signupLayout
                val otherView = if (isLoginTab) binding.signupLayout else binding.loginLayout

                targetView.visibility = View.VISIBLE
                targetView.animate().alpha(1f).setDuration(300).start()
                otherView.animate().alpha(0f).setDuration(300).withEndAction {
                    otherView.visibility = View.GONE
                }.start()
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun setupValidationListeners() {
        binding.loginEmailInput.doAfterTextChanged { validateLoginForm() }
        binding.loginPasswordInput.doAfterTextChanged { validateLoginForm() }

        binding.signupNameInput.doAfterTextChanged { validateSignupForm() }
        binding.signupEmailInput.doAfterTextChanged { validateSignupForm() }
        binding.signupPhoneInput.doAfterTextChanged { validateSignupForm() }
        binding.signupPasswordInput.doAfterTextChanged { validateSignupForm() }
        binding.signupConfirmPasswordInput.doAfterTextChanged { validateSignupForm() }

        binding.termsCheckbox.setOnCheckedChangeListener { _, _ -> validateSignupForm() }
    }

    private fun validateLoginForm() {
        val email = binding.loginEmailInput.text.toString().trim()
        val password = binding.loginPasswordInput.text.toString().trim()
        val isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val isPasswordValid = password.isNotEmpty()
        updateButtonState(binding.loginButton, isEmailValid && isPasswordValid)
    }

    private fun validateSignupForm() {
        val password = binding.signupPasswordInput.text.toString().trim()
        val confirmPassword = binding.signupConfirmPasswordInput.text.toString().trim()

        if (confirmPassword.isNotEmpty() && password != confirmPassword) {
            binding.confirmPasswordLayout.error = "비밀번호가 일치하지 않습니다."
        } else {
            binding.confirmPasswordLayout.error = null
        }

        val isFormValid = binding.signupNameInput.text.toString().isNotBlank() &&
                android.util.Patterns.EMAIL_ADDRESS.matcher(binding.signupEmailInput.text.toString().trim()).matches() &&
                binding.signupPhoneInput.text.toString().isNotBlank() &&
                password.length >= 8 &&
                password == confirmPassword &&
                binding.termsCheckbox.isChecked
        updateButtonState(binding.signupButton, isFormValid)
    }

    private fun updateButtonState(button: MaterialButton, isEnabled: Boolean) {
        button.isEnabled = isEnabled
        button.alpha = if (isEnabled) 1.0f else 0.5f
    }

    private fun setupClickListeners() {
        binding.loginButton.setOnClickListener {
            viewModel.performLogin(
                binding.loginEmailInput.text.toString(),
                binding.loginPasswordInput.text.toString()
            )
        }
        binding.signupButton.setOnClickListener {
            viewModel.performSignup(
                binding.signupNameInput.text.toString(),
                binding.signupEmailInput.text.toString(),
                binding.signupPhoneInput.text.toString(),
                binding.signupPasswordInput.text.toString()
            )
        }
        binding.forgotPasswordText.setOnClickListener {
            Toast.makeText(this, "비밀번호 찾기 화면으로 이동합니다.", Toast.LENGTH_SHORT).show()
        }
        binding.kakaoButton.setOnClickListener { performSocialLogin("카카오") }
        binding.naverButton.setOnClickListener { performSocialLogin("네이버") }
        binding.googleButton.setOnClickListener { performSocialLogin("구글") }
    }

    private fun observeViewModel() {
        viewModel.loginState.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.loginButton.text = "로그인 중..."
                    updateButtonState(binding.loginButton, false)
                }
                is UiState.Success -> {
                    Toast.makeText(this, state.data, Toast.LENGTH_SHORT).show()
                    binding.loginButton.text = "로그인"
                    validateLoginForm()
                }
                is UiState.Error -> {
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                    binding.loginButton.text = "로그인"
                    validateLoginForm()
                }
                else -> { /* Idle 상태 */ }
            }
        }
        viewModel.signupState.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.signupButton.text = "가입 중..."
                    updateButtonState(binding.signupButton, false)
                }
                is UiState.Success -> {
                    Toast.makeText(this, state.data, Toast.LENGTH_SHORT).show()
                    binding.signupButton.text = "회원가입"
                    binding.tabLayout.getTabAt(0)?.select()
                }
                is UiState.Error -> {
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                    binding.signupButton.text = "회원가입"
                    validateSignupForm()
                }
                else -> { /* Idle 상태 */ }
            }
        }
    }

    private fun performSocialLogin(provider: String) {
        Toast.makeText(this, "$provider 로그인을 시도합니다.", Toast.LENGTH_SHORT).show()
    }
}