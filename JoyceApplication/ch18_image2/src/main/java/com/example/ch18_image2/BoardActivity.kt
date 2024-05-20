package com.example.ch18_image2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ch18_image2.databinding.ActivityBoardBinding

class BoardActivity : AppCompatActivity() {
    lateinit var binding : ActivityBoardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainFab.setOnClickListener(){
            if(MyApplication.checkAuth()){
                startActivity(Intent(this, AddActivity::class.java))
            }
            else{
                Toast.makeText(this,"인증을 먼저 진행해주세요..", Toast.LENGTH_LONG)
            }
        }
    }
}