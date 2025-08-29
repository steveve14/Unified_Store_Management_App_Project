package com.example.deliveryapp.data.model

data class Order(
    val id: String,
    val storeName: String,
    val orderDate: String,
    val status: String, // 예: "Delivered", "Cancelled"
    val menuSummary: String, // 예: "Kimchi Jjigae and 1 other item"
    val price: Int,
    val imageUrl: String
)