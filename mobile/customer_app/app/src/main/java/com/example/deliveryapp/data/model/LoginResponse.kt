package com.example.deliveryapp.data.model

data class LoginResponse(
    val token: String,
    val userId: String,
    val name: String
)