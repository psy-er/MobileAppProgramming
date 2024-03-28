package com.example.eventapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.eventapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 기본 코틀린 코드
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // 위아래 같은 방식임
        //setContentView(R.layout.activity_main)


        // 코틀린에서도 위젯의 성질을 변화시킬 수 있다.
        binding.startButton.text = "시작"
        binding.startButton.textSize = 24.0f
        binding.startButton.setOnClickListener {

        }
        binding.stopButton.setOnClickListener{
        }
        binding.resetButton.setOnClickListener {

        }
    }
}