package com.example.ch13_activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ch13_activity.databinding.ActivityMainBinding
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    var datas : MutableList<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // outState에서 저장한 값이 있으면 사용하겠다
        datas = savedInstanceState?.let {
            it.getStringArrayList("datas")?.toMutableList()
        } ?: let{
            mutableListOf<kotlin.String>()
        }

        val adapter = MyAdapter(datas)
        binding.recyclerView.adapter = adapter //MyAdapter(datas)

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        )

        // AddActivity에서 값이 들어오면
        val requestLauncher: ActivityResultLauncher<Intent>
        = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        {
            it.data!!.getStringExtra("result")?.let{
                if(it != ""){
                    datas?.add(it)
                    adapter.notifyDataSetChanged() // 바뀐표시
                }
            }
        }

        // 안드로이드에서 시스템쪽으로 인텐트를 전달
        binding.mainFab.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)

            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            intent.putExtra("today", dateFormat.format(System.currentTimeMillis()))

            //startActivity(intent)
            requestLauncher.launch(intent)
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // 데이터를 저장한다.
        outState.putStringArrayList("datas", ArrayList(datas))
    }
}