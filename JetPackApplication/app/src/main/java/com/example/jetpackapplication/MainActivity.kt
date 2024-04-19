package com.example.jetpackapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.jetpackapplication.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding

    class MyFragmentPagerAdapter(activity: FragmentActivity): FragmentStateAdapter(activity){ // 상속
        val fragments : List<Fragment> // 3개의 Fragment 저장
        init{
            fragments = listOf(OneFragment(), TwoFragment(), ThreeFragment())
        }
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

        // 뷰 페이저와 Adapter 연결
        binding.viewpager.adapter = MyFragmentPagerAdapter(this)

        // 뷰 페이저와 Tab 연결
        TabLayoutMediator(binding.tabs, binding.viewpager){
            tab, position ->
                tab.text = "TAB ${position+1}"
        }.attach()
    }
}