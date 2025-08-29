package com.example.deliveryapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.deliveryapp.R
import com.example.deliveryapp.data.model.Category
import com.example.deliveryapp.data.model.Restaurant

class HomeViewModel : ViewModel() {

    // UI가 관찰할 LiveData
    val categories: LiveData<List<Category>> get() = _categories
    private val _categories = MutableLiveData<List<Category>>()

    val restaurants: LiveData<List<Restaurant>> get() = _restaurants
    private val _restaurants = MutableLiveData<List<Restaurant>>()

    init {
        loadData()
    }

    private fun loadData() {
        // TODO: 여기서 Repository를 통해 실제 서버 API를 호출해야 함

        // 임시 데이터 로드
        _categories.value = getDummyCategories()
        _restaurants.value = getDummyRestaurants()
    }

    private fun getDummyCategories(): List<Category> {
        return listOf(
            Category(R.drawable.fastfood, "Korean"),
            Category(R.drawable.fastfood, "Chinese"),
            Category(R.drawable.local_pizza, "Pizza"),
            Category(R.drawable.lunch_dining, "Chicken"),
            Category(R.drawable.restaurant, "Japanese"),
            Category(R.drawable.cake, "Dessert")
        )
    }

    private fun getDummyRestaurants(): List<Restaurant> {
        return listOf(
            Restaurant("1", "Steve's BBQ", 4.9, 1024, "20-30 min", 3000, "url_to_image"),
            Restaurant("2", "Pizza Heaven", 4.8, 876, "30-40 min", 2000, "url_to_image"),
            Restaurant("3", "Healthy Salads", 4.9, 512, "15-25 min", 4000, "url_to_image")
        )
    }
}