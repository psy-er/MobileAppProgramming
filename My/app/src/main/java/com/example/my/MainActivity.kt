package com.example.my

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.my.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var binding : ActivityMainBinding
    lateinit var toggle : ActionBarDrawerToggle

    class MyFragmentPagerAdapter(activity: FragmentActivity): FragmentStateAdapter(activity){ // 상속

        val fragments : List<Fragment> // 3개의 Fragment 저장

        init{ fragments = listOf(frag1(), frag2(), frag3()) }

        override fun getItemCount(): Int {
            return fragments.size
        }
        // 0->onefragment, 1->twofragment, 2->threefragment
        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Drawerlayout과 연결, binding.id(drawer)와 연결, res에 있는 string 불러오기
        toggle = ActionBarDrawerToggle(this, binding.drawer ,R.string.drawer_opened, R.string.drawer_closed )
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 화면에 보여짐
        toggle.syncState()


        // 드로어 레이아웃 아이템이 클릭할 때마다 리스너가 붙여진다
        binding.mainDrawerView.setNavigationItemSelectedListener(this)


        //////////////////////////////////////////////////////

        // 뷰 페이저와 Adapter 연결
        binding.viewpager.adapter = MyFragmentPagerAdapter(this)

        // 뷰 페이저와 Tab 연결
        TabLayoutMediator(binding.tabs, binding.viewpager){
                tab, position -> tab.text = "TAB ${position+1}"
        }.attach()

    } // onCreate()





    // 네비게이션 바 아이템 선택하면
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.item_info -> {
                //Log.d("mobileapp", "Navigation Menu : 메뉴 1")
                //binding.btnDate.setTextColor(Color.parseColor("#ffff00"))
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://m.duksung.ac.kr"))
                startActivity(intent)
                true
            }
            R.id.item_map -> {
                //Log.d("mobileapp", "Navigation Menu : 메뉴 2")
                // 지도 불러오기
                //val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.5662952, 126.9779451"))

                // 길찾기
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/dir/서울역/수유역"))
                startActivity(intent)
                true
            }
            R.id.item_gallery -> {
                //Log.d("mobileapp", "Navigation Menu : 메뉴 3")
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("content://media/internal/images/media"))
                startActivity(intent)
                true
            }
        }
        return false
    }
    // [서치 메뉴] Option Menu
    // onCreateOptionMenu는 미리 만들어 놓은 메뉴를 코틀린이 사용할 수 있게 만듦
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // 메뉴 화면 구성 가지고 옴
        // res/menu/menu.navigation을 가지고 와 menu에 저장
        menuInflater.inflate(R.menu.menu_navigation, menu)

        // 여러가지 액션뷰 중에 서치뷰 지정
        val searchView = menu?.findItem(R.id.menu_search)?.actionView as SearchView // null 가능, as 캐스트

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(applicationContext, "$query 검색합니다.", Toast.LENGTH_LONG).show()
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }


    // 옵션 선택했을 때 동작
    // MenuItem은 어떤 아이템이 선택됐는지 받음
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        // 토글 본래의 기능을 실행
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        /*
        when(item.itemId){

            R.id.item1 -> {
                Log.d("mobileapp", "Option Menu : 메뉴 1")
                binding.btnDate.setTextColor(Color.parseColor("#ffff00"))
                true
            }
            R.id.item2 -> {
                Log.d("mobileapp", "Option Menu : 메뉴 2")
                true
            }
            R.id.item3 -> {
                Log.d("mobileapp", "Option Menu : 메뉴 3")
                true
            }
            R.id.item4 -> {
                Log.d("mobileapp", "Option Menu : 메뉴 4")
                true
            }
        }*/
        return super.onOptionsItemSelected(item)
    }
}