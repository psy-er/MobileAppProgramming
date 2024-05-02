package com.example.ch17_storage

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ch17_storage.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.File
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    var datas: MutableList<String>? = null
    lateinit var adapter: MyAdapter
    lateinit var sharedPreference : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreference = PreferenceManager.getDefaultSharedPreferences(this)
        val color = sharedPreference.getString("color","#ffff00")
        binding.lastsaved.setBackgroundColor(Color.parseColor(color))

        var idStr = sharedPreference.getString("id","")
        binding.todoTitle.text = idStr

        val size = sharedPreference.getString("size","16.0f")
        binding.lastsaved.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size!!.toFloat())

        datas = mutableListOf<String>()
        val db = DBHelper(this).readableDatabase
        val cursor = db.rawQuery("select * from todo_tb", null)
        while(cursor.moveToNext()){
            datas?.add(cursor.getString(1))
        }
        db.close()

        /*
        datas  = savedInstanceState?.let {
            it.getStringArrayList("datas")?.toMutableList()
        }
            ?: let {
                mutableListOf<String>()
            }
        */
        ///////////////////////////////////////////////////////////////////////
        // 파일 읽기 wipe 데이터 하면 파일 삭제! 파일 읽어오는데 오류가 생기니깐 주의하자!
        val file = File(filesDir, "test.txt")
        val readstream: BufferedReader = file.reader().buffered()
        binding.lastsaved.text = "마지막 저장시간 : " + readstream.readLine()

        adapter = MyAdapter(datas)
        binding.recyclerView.adapter=adapter
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager=layoutManager
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        val requestLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
            it.data!!.getStringExtra("result")?.let {// "result"에 값이 저장되어 있으면(non-null)
                if(it != "") {
                    datas?.add(it)
                    adapter.notifyDataSetChanged()
                }
            }
        }

        binding.mainFab.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)

            val dateFormat = SimpleDateFormat("yyyy-MM-dd") // 년 월 일
            intent.putExtra("today",dateFormat.format(System.currentTimeMillis()))

            requestLauncher.launch(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_setting, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId === R.id.menu_main_setting) {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    } // onCreate()

    override fun onResume() {
        super.onResume()

        sharedPreference = PreferenceManager.getDefaultSharedPreferences(this)
        val color = sharedPreference.getString("color","#ffff00")
        binding.lastsaved.setBackgroundColor(Color.parseColor(color))

        var idStr = sharedPreference.getString("id","")
        binding.todoTitle.text = idStr

        val size = sharedPreference.getString("size","16.0f")
        binding.lastsaved.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size!!.toFloat())

    }

    /*
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList("datas", ArrayList(datas))  // 지금까지의 datas 저장
    }*/
}