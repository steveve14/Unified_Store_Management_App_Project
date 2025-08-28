package com.example.deliveryapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 위에서 만든 activity_main.xml 화면을 표시합니다.
        setContentView(R.layout.activity_main)
    }
}