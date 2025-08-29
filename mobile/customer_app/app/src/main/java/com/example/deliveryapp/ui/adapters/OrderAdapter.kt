package com.example.deliveryapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.deliveryapp.data.model.Order
import com.example.deliveryapp.databinding.ItemOrderHistoryBinding

class OrderAdapter(
    private val orders: List<Order>,
    private val onItemClick: (Order) -> Unit // 아이템 클릭을 위한 람다 함수
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    class OrderViewHolder(private val binding: ItemOrderHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(order: Order, onItemClick: (Order) -> Unit) {
            // 데이터 바인딩
            binding.tvStoreName.text = order.storeName
            binding.tvOrderDate.text = order.orderDate
            binding.tvOrderStatus.text = order.status
            binding.tvOrderSummary.text = "${order.menuSummary} · ₩${order.price}"
            // Glide.with(itemView.context).load(order.imageUrl).into(binding.ivStoreImage)

            // 아이템 뷰 전체에 클릭 리스너 설정
            itemView.setOnClickListener {
                onItemClick(order)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding =
            ItemOrderHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(orders[position], onItemClick)
    }

    override fun getItemCount(): Int = orders.size
}