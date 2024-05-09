package com.example.joyceapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.joyceapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val jsonfragment = JsonFragment()
        val xmlfragment = XmlFragment()
        val bundle = Bundle()

        binding.btnSearch.setOnClickListener {
            bundle.putString("searchYear", binding.edtYear.text.toString())

            if (binding.rGroup.checkedRadioButtonId == R.id.rbJson) {
                jsonfragment.arguments = bundle
                supportFragmentManager.beginTransaction()
                    .replace(R.id.activity_content, jsonfragment)
                    .commit()
            } else if (binding.rGroup.checkedRadioButtonId == R.id.rbXml) {
                xmlfragment.arguments = bundle
                supportFragmentManager.beginTransaction()
                    .replace(R.id.activity_content, xmlfragment)
                    .commit()
            }
        }
    }
}