package com.example.deliveryapp.ui.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deliveryapp.data.model.Order
import com.example.deliveryapp.databinding.FragmentOrderListBinding
import com.example.deliveryapp.ui.adapters.OrderAdapter

// 실제 주문 목록을 보여주는 재사용 가능한 프래그먼트
class OrderListFragment : Fragment() {

    private var _binding: FragmentOrderListBinding? = null
    private val binding get() = _binding!!

    // 이 프래그먼트가 '현재 주문'을 보여줄지 '과거 주문'을 보여줄지 결정하는 변수
    private var isCurrentOrders: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // newInstance를 통해 전달된 인자(argument)를 받습니다.
        arguments?.let {
            isCurrentOrders = it.getBoolean(ARG_IS_CURRENT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.orderRecyclerView.layoutManager = LinearLayoutManager(context)

        // TODO: ViewModel을 통해 서버에서 실제 주문 내역을 가져와야 합니다.
        if (isCurrentOrders) {
            // 현재 주문 내역 로드 (임시 데이터)
            val dummyCurrentOrders = listOf(
                Order("3", "Chicken World", "2025.08.29", "Cooking", "Fried Chicken", 18000, "")
            )
            binding.orderRecyclerView.adapter = OrderAdapter(dummyCurrentOrders) { order ->
                Toast.makeText(
                    context,
                    "Clicked on current order: ${order.storeName}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            // 과거 주문 내역 로드 (임시 데이터)
            val dummyPastOrders = listOf(
                Order("1", "Steve's Pizza", "2025.08.28", "Delivered", "Pepperoni Pizza and 1 other", 25000, ""),
                Order("2", "Korean BBQ", "2025.08.27", "Delivered", "Pork Belly and 3 others", 55000, "")
            )
            binding.orderRecyclerView.adapter = OrderAdapter(dummyPastOrders) { order ->
                Toast.makeText(context, "Clicked on past order: ${order.storeName}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_IS_CURRENT = "is_current"

        // 프래그먼트 생성 시 인자를 안전하게 전달하기 위한 newInstance 패턴
        @JvmStatic
        fun newInstance(isCurrentOrders: Boolean) =
            OrderListFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_IS_CURRENT, isCurrentOrders)
                }
            }
    }
}