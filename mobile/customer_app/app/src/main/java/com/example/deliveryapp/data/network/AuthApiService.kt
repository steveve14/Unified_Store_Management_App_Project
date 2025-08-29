package com.example.deliveryapp.data.network

import com.example.deliveryapp.data.model.LoginRequest
import com.example.deliveryapp.data.model.LoginResponse
import com.example.deliveryapp.data.model.SignUpRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApiService {

    @Headers("Content-Type: application/json")
    @POST("users/register")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<Unit> // 회원가입은 보통 성공 여부만 받음

    @Headers("Content-Type: application/json")
    @POST("users/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
}