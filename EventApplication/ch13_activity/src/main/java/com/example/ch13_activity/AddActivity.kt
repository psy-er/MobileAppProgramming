package com.example.ch13_activity

import android.app.Activity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ch13_activity.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 나를 호출한 인텐트에서 today라는 값을 전달 받는다
        var date = intent.getStringExtra("today")
        binding.date.text = date

        // 액션바에 Back 버튼이 있으면 추가
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // save 버튼을 클릭하면 나를 불렀던 메인 엑티비티로 다시 되돌아 간다.
        binding.btnSave.setOnClickListener {
            // 결과를 가지고 와서 넘기기
            val intent = intent
            intent.putExtra("result", binding.addEditView.text.toString())
            setResult(Activity.RESULT_OK, intent)

            finish()
            true
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        val intent = intent
        // setResult 함수로 되돌아 가겠다.
        setResult(Activity.RESULT_OK, intent)

        finish()
        return true
    }
}