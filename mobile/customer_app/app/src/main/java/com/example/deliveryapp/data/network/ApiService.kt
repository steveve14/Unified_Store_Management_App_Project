package com.example.deliveryapp.data.network

import com.example.deliveryapp.data.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

// API 응답을 위한 제네릭 래퍼 클래스
data class ApiResponse<T>(
    val success: Boolean,
    val data: T?,
    val message: String?
)

interface ApiService {

    // --- Auth ---
    @Headers("Content-Type: application/json")
    @POST("users/register")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<ApiResponse<Unit>>

    @Headers("Content-Type: application/json")
    @POST("users/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    // --- Main Screen ---
    @GET("stores")
    suspend fun getStores(): Response<ApiResponse<List<Restaurant>>>

    @GET("orders") // 실제로는 /orders/me 와 같이 사용자 특정 주문을 가져와야 함
    suspend fun getMyOrders(): Response<ApiResponse<List<Order>>>

    // TODO: Banner, Category API 추가
}