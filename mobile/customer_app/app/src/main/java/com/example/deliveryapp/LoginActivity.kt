package com.example.deliveryapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.example.deliveryapp.databinding.ActivityLoginBinding // 생성된 바인딩 클래스를 임포트
import com.google.android.material.tabs.TabLayout

class LoginActivity : AppCompatActivity() {

    // 뷰 바인딩을 위한 변수 선언
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 뷰 바인딩 초기화 및 화면 설정
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 기능별 함수 호출
        setupTabLayout()
        setupInputValidation()
        setupClickListeners()
        setupSocialLoginButtons() // 소셜 로그인 버튼 아이콘 설정
    }

    /**
     * 탭 레이아웃 설정 (로그인/회원가입 전환)
     */
    private fun setupTabLayout() {
        // 탭 추가
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("로그인"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("회원가입"))

        // 탭 선택 리스너
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> { // 로그인 탭
                        binding.loginLayout.visibility = View.VISIBLE
                        binding.signupLayout.visibility = View.GONE
                    }
                    1 -> { // 회원가입 탭
                        binding.loginLayout.visibility = View.GONE
                        binding.signupLayout.visibility = View.VISIBLE
                    }
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    /**
     * 입력 필드 유효성 검사 설정
     */
    private fun setupInputValidation() {
        // --- 로그인 폼 유효성 검사 ---
        binding.loginEmailInput.doAfterTextChanged { validateLoginForm() }
        binding.loginPasswordInput.doAfterTextChanged { validateLoginForm() }

        // --- 회원가입 폼 유효성 검사 ---
        binding.signupNameInput.doAfterTextChanged { validateSignupForm() }
        binding.signupEmailInput.doAfterTextChanged { validateSignupForm() }
        binding.signupPhoneInput.doAfterTextChanged { validateSignupForm() }
        binding.signupPasswordInput.doAfterTextChanged { validateSignupForm() }
        binding.signupConfirmPasswordInput.doAfterTextChanged { validateSignupForm() }
        binding.termsCheckbox.setOnCheckedChangeListener { _, _ -> validateSignupForm() }
    }

    /**
     * 로그인 폼의 입력값을 확인하고 버튼 상태를 업데이트
     */
    private fun validateLoginForm() {
        val email = binding.loginEmailInput.text.toString()
        val password = binding.loginPasswordInput.text.toString()
        val isEnabled = email.isNotBlank() && password.isNotBlank()
        updateButtonState(binding.loginButton, isEnabled)
    }

    /**
     * 회원가입 폼의 입력값을 확인하고 버튼 상태를 업데이트
     */
    private fun validateSignupForm() {
        val name = binding.signupNameInput.text.toString()
        val email = binding.signupEmailInput.text.toString()
        val phone = binding.signupPhoneInput.text.toString()
        val password = binding.signupPasswordInput.text.toString()
        val confirmPassword = binding.signupConfirmPasswordInput.text.toString()
        val termsChecked = binding.termsCheckbox.isChecked

        // 비밀번호 일치 여부 확인 및 에러 메시지 설정
        if (password.isNotBlank() && confirmPassword.isNotBlank() && password != confirmPassword) {
            binding.confirmPasswordLayout.error = "비밀번호가 일치하지 않습니다."
        } else {
            binding.confirmPasswordLayout.error = null // 일치하면 에러 메시지 제거
        }

        val isEnabled = name.isNotBlank() && email.isNotBlank() && phone.isNotBlank() &&
                password.length >= 8 && password == confirmPassword && termsChecked

        updateButtonState(binding.signupButton, isEnabled)
    }

    /**
     * 버튼의 활성화 상태와 투명도를 업데이트
     * @param button 상태를 변경할 버튼
     * @param isEnabled 활성화 여부
     */
    private fun updateButtonState(button: View, isEnabled: Boolean) {
        button.isEnabled = isEnabled
        button.alpha = if (isEnabled) 1.0f else 0.5f
    }

    /**
     * 각종 클릭 이벤트 리스너 설정
     */
    private fun setupClickListeners() {
        binding.loginButton.setOnClickListener {
            // TODO: 실제 로그인 API 호출 로직 구현
            Toast.makeText(this, "로그인을 시도합니다.", Toast.LENGTH_SHORT).show()
        }

        binding.signupButton.setOnClickListener {
            // TODO: 실제 회원가입 API 호출 로직 구현
            Toast.makeText(this, "회원가입을 진행합니다.", Toast.LENGTH_SHORT).show()
        }

        binding.forgotPasswordText.setOnClickListener {
            Toast.makeText(this, "비밀번호 찾기 화면으로 이동합니다.", Toast.LENGTH_SHORT).show()
        }

        binding.kakaoButton.setOnClickListener { socialLogin("카카오") }
        binding.naverButton.setOnClickListener { socialLogin("네이버") }
        binding.googleButton.setOnClickListener { socialLogin("구글") }
    }

    /**
     * 소셜 로그인 버튼에 아이콘 설정 (아이콘 파일이 drawable 폴더에 있어야 함)
     */
    private fun setupSocialLoginButtons() {
        // 아래 R.drawable.ic_... 부분에 실제 이미지 파일 이름을 넣으세요.
        // binding.kakaoButton.setImageResource(R.drawable.ic_kakao_login)
        // binding.naverButton.setImageResource(R.drawable.ic_naver_login)
        // binding.googleButton.setImageResource(R.drawable.ic_google_login)
    }

    /**
     * 소셜 로그인 처리 (임시)
     */
    private fun socialLogin(provider: String) {
        Toast.makeText(this, "$provider 계정으로 로그인을 시도합니다.", Toast.LENGTH_SHORT).show()
    }
}