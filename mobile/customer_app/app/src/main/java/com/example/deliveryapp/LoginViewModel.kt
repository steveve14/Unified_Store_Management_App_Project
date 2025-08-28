package com.example.deliveryapp // 본인의 패키지 이름 확인

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// UI의 다양한 상태를 표현하기 위한 클래스 (성공, 실패, 로딩 등)
sealed class UiState<out T> {
    object Idle : UiState<Nothing>() // 초기 상태
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

class LoginViewModel : ViewModel() {

    // 로그인 UI 상태를 관리 (Activity에서는 이 LiveData를 관찰)
    private val _loginState = MutableLiveData<UiState<String>>()
    val loginState: LiveData<UiState<String>> = _loginState

    // 회원가입 UI 상태를 관리
    private val _signupState = MutableLiveData<UiState<String>>()
    val signupState: LiveData<UiState<String>> = _signupState

    /**
     * 로그인 비즈니스 로직 처리
     */
    fun performLogin(email: String, password: String) {
        viewModelScope.launch { // 비동기 작업을 위한 코루틴 스코프
            _loginState.value = UiState.Loading
            delay(1500) // 1.5초 지연으로 실제 네트워크 API 호출을 시뮬레이션

            // TODO: 여기에 실제 로그인 API 호출 및 결과 처리 로직 구현
            // 성공했을 경우
            _loginState.value = UiState.Success("로그인 성공!")
            // 실패했을 경우
            // _loginState.value = UiState.Error("이메일 또는 비밀번호가 올바르지 않습니다.")
        }
    }

    /**
     * 회원가입 비즈니스 로직 처리
     */
    fun performSignup(name: String, email: String, phone: String, password: String) {
        viewModelScope.launch {
            _signupState.value = UiState.Loading
            delay(1500) // API 호출 시뮬레이션

            // TODO: 여기에 실제 회원가입 API 호출 및 결과 처리 로직 구현
            _signupState.value = UiState.Success("회원가입이 완료되었습니다.")
        }
    }
}