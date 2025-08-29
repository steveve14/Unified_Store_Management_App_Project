package com.example.deliveryapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.deliveryapp.data.model.Restaurant
import com.example.deliveryapp.databinding.ItemRestaurantBinding
// import com.bumptech.glide.Glide // 이미지 로딩을 위해 Glide 같은 라이브러리 사용 추천

class RestaurantAdapter(private val restaurants: List<Restaurant>) :
    RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    class RestaurantViewHolder(private val binding: ItemRestaurantBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(restaurant: Restaurant) {
            binding.tvRestaurantName.text = restaurant.name
            binding.tvRating.text = "${restaurant.rating} (${restaurant.reviewCount})"
            binding.tvDeliveryInfo.text = "${restaurant.deliveryTime} · ₩${restaurant.deliveryFee}"
            // Glide.with(itemView.context).load(restaurant.imageUrl).into(binding.ivRestaurantImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val binding =
            ItemRestaurantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RestaurantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(restaurants[position])
    }

    override fun getItemCount(): Int = restaurants.size
}