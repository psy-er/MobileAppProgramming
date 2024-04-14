package com.example.myfirstapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() { // AppCompatActivity 상속

    // onCreate 함수가 가장 먼저 실행된다.
    override fun onCreate(savedInstanceState: Bundle?) {
        // super을 이용해 상속 받는 onCreate를 그대로 부른다.
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // ContentView를 구성한다.
        // 하나의 앱을 실행시키기 위해서 최소 1개의 xml과 1개의 코틀린 파일이 필요하다.
        // res/layout/activity_main을 불러온다.
        setContentView(R.layout.activity_main)
    }
}