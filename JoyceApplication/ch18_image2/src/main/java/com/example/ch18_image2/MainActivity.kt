package com.example.ch18_image2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ch18_image2.RetrofitConnection
import com.example.ch18_image2.XmlAdapter
import com.example.ch18_image2.XmlResponse
import com.example.ch18_image2.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener { // Drawer 메뉴
    lateinit var binding: ActivityMainBinding

    // DrawerLayout Toggle
    lateinit var toggle: ActionBarDrawerToggle

    lateinit var headerView : View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 0
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // DrawerLayout Toggle
        toggle = ActionBarDrawerToggle(
            this,
            binding.drawer,
            R.string.drawer_opened,
            R.string.drawer_closed
        )
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle.syncState()

        // Drawer 메뉴
        binding.mainDrawerView.setNavigationItemSelectedListener(this)

        headerView = binding.mainDrawerView.getHeaderView(0)
        val button = headerView.findViewById<Button>(R.id.btnAuth)
        button.setOnClickListener {
            Log.d("mobileapp", "button.setOnClickListener")

            val intent = Intent(this, AuthActivity::class.java)
            if(button.text.equals("로그인")){
                intent.putExtra("status", "logout")
            }
            else if(button.text.equals("로그아웃")){
                intent.putExtra("status", "login")
            }
            startActivity(intent)

            binding.drawer.closeDrawers()
        }

        binding.btnSearch.setOnClickListener{
            if(MyApplication.checkAuth()){
                val name = binding.edtName.text.toString()
                Log.d("mobileapp", name)

                val call: Call<XmlResponse> = RetrofitConnection.xmlNetworkService.getXmlList(
                    name,
                    1,
                    10,
                    "xml",
                    "eAwLcv+UvTothYoSGVNMD0RoGDfKckaMKN4LiBfwtnRi/z0i5/MEN3OgHv/JeKUJ7T0WFsE3p3bHIio0q5Hm0Q==" // 일반인증키(Decoding)
                )

                call?.enqueue(object : Callback<XmlResponse> {
                    override fun onResponse(call: Call<XmlResponse>, response: Response<XmlResponse>) {
                        if(response.isSuccessful){
                            Log.d("mobileapp", "$response")
                            Log.d("mobileapp", "${response.body()}")
                            binding.xmlRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
                            binding.xmlRecyclerView.adapter = XmlAdapter(response.body()!!.body!!.items!!.item)
                            binding.xmlRecyclerView.addItemDecoration(DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL))
                        }
                    }

                    override fun onFailure(call: Call<XmlResponse>, t: Throwable) {
                        Log.d("mobileapp", "onFailure ${call.request()}")
                    }
                })
            }
            else{
                Toast.makeText(this, "인증을 먼저 진행해주세요..",Toast.LENGTH_LONG).show()
            }

        } // binding.btnSearch.setOnClickListener

    }

    // DrawerLayout Toggle
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    // Drawer 메뉴
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_board ->{
                Log.d("mobileapp", "게시판 메뉴")
                val intent = Intent(this, BoardActivity::class.java)
                startActivity(intent)
                binding.drawer.closeDrawers()
                true
            }
            R.id.item_setting -> {
                Log.d("mobileapp", "설정 메뉴")
                //val intent = Intent(this, SettingActivity::class.java)
                //startActivity(intent)
                binding.drawer.closeDrawers()
                true
            }
        }
        return false
    }

    override fun onStart() {
        super.onStart()

        val button = headerView.findViewById<Button>(R.id.btnAuth)
        val tv = headerView.findViewById<TextView>(R.id.tvID)

        if(MyApplication.checkAuth()){
            button.text = "로그아웃"
            tv.text = "${MyApplication.email}님 \n 반갑습니다."
        }
        else{
            button.text = "로그인"
            tv.text = "안녕하세요.."
        }
    }
}