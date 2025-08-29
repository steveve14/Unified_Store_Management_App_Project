package com.example.deliveryapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.deliveryapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCategoryRecyclerView()
        // TODO: Banner ViewPager2 어댑터 설정
    }

    private fun setupCategoryRecyclerView() {
        // 임시 데이터
        val categories = listOf(
            Category(R.drawable.home, "Korean"), // 아이콘은 임시
            Category(R.drawable.home, "Chinese"),
            Category(R.drawable.home, "Japanese"),
            Category(R.drawable.home, "Western"),
            Category(R.drawable.home, "Pizza"),
            Category(R.drawable.home, "Chicken"),
            Category(R.drawable.home, "Dessert"),
            Category(R.drawable.home, "Snacks")
        )

        val categoryAdapter = CategoryAdapter(categories)
        binding.categoryRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 4) // 한 줄에 4개씩
            adapter = categoryAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

// 임시 데이터 클래스 (별도 파일로 분리하는 것을 추천)
data class Category(val iconResId: Int, val name: String)

// 카테고리 어댑터 (별도 파일로 분리하는 것을 추천)
// ... CategoryAdapter 클래스 구현 ...