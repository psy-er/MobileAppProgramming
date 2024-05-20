package com.example.ch18_image2

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ch18_image2.databinding.ActivityAddBinding
import java.text.SimpleDateFormat

class AddActivity : AppCompatActivity() {
    lateinit var binding : ActivityAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvId.text = MyApplication.email

        binding.saveButton.setOnClickListener{
            if(binding.input.text.isNotEmpty()){
                // 로그인 이메일, 스타, 한줄평, 입력 시간
                val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                val data = mapOf(
                    "email" to MyApplication.email,
                    "stars" to binding.ratingBar.rating.toFloat(),
                    "comments" to binding.input.text.toString(),
                    "date_time" to dateFormat.format(System.currentTimeMillis())
                )

                MyApplication.db.collection("comments")
                    .add(data)
                    .addOnSuccessListener{
                        Toast.makeText(this,"데이터 저장 성공", Toast.LENGTH_LONG).show()
                        finish()
                    }
                    .addOnFailureListener{
                        Toast.makeText(this,"데이터 저장 실패", Toast.LENGTH_LONG).show()
                    }

            }
            else{
                Toast.makeText(this,"한줄평을 먼저 입력해주세요..", Toast.LENGTH_LONG).show()
            }
        }
    }
}