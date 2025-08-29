package com.example.deliveryapp.data.model

data class Restaurant(
    val id: String,
    val name: String,
    val rating: Double,
    val reviewCount: Int,
    val deliveryTime: String,
    val deliveryFee: Int,
    val imageUrl: String
)