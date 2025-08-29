package com.example.deliveryapp.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.deliveryapp.ui.auth.AuthActivity
import com.example.deliveryapp.data.local.SessionManager
import com.example.deliveryapp.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 저장된 사용자 정보로 UI 업데이트
        loadUserInfo()
        setupClickListeners()
    }

    private fun loadUserInfo() {
        binding.tvUserName.text = SessionManager.fetchUserName(requireContext())
        binding.tvUserEmail.text = SessionManager.fetchUserEmail(requireContext())
    }

    private fun setupClickListeners() {
        binding.menuLogout.setOnClickListener {
            // 세션 데이터 삭제
            SessionManager.clearData(requireContext())

            // AuthActivity로 이동하고, 이전의 모든 액티비티 스택을 지움
            val intent = Intent(activity, AuthActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        binding.menuEditProfile.setOnClickListener {
            Toast.makeText(context, "Edit Profile Clicked", Toast.LENGTH_SHORT).show()
        }
        // ... 다른 메뉴 아이템들의 리스너
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}