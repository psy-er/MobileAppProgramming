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

        var date = intent.getStringExtra("today")
        binding.date.text = date

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnSave.setOnClickListener {
            val intent = intent
            intent.putExtra("result", binding.addEditView.text.toString())
            setResult(Activity.RESULT_OK, intent)

            finish()
            true
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp()
    }
}