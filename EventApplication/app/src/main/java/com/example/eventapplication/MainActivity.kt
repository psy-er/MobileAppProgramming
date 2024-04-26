package com.example.eventapplication

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.eventapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // 전역변수를 설정해서 백키누른 횟수 고려
    var initTime = 0L

    // onCreate 함수에서는 위젯에서 발생할 수 있는 이벤트를 설정한다
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // build.gradle에 view Binding 설정을 해서 ActivityMainBinding 클래스 자동 생성
        // inflate 함수를 사용해서 XML파일을 코틀린에 불러옴
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setContentView(R.layout.activity_main) 대체 가능

        // stop한 시간을 알려주기 위한 변수
        var prevTime = 0L

        // Activity_main에 있는 버튼들을 바인딩에서 불러와 사용 가능
        // 코틀린에서도 위젯의 성질을 변화시킬 수 있다.
        binding.startButton.text = "시작"
        binding.startButton.textSize = 24.0f

        // 코틀린에서도 위젯의 성질을 변화시킬 수 있다.
        // 힌트 view 타입의 it이라는 변수를 쓰겠다.
        // 버튼 활성화와 비활성화
        binding.startButton.setOnClickListener {
            binding.chronometer.base = SystemClock.elapsedRealtime() + prevTime
            binding.chronometer.start()

            binding.startButton.isEnabled = false
            binding.stopButton.isEnabled = true
            binding.resetButton.isEnabled = true
        }
        binding.stopButton.setOnClickListener{
            // start~stop까지 시간 저장
            prevTime = binding.chronometer.base - SystemClock.elapsedRealtime()
            binding.chronometer.stop()

            binding.stopButton.isEnabled = false
            binding.startButton.isEnabled = true
            binding.resetButton.isEnabled = false
        }
        binding.resetButton.setOnClickListener {
            prevTime = 0L
            binding.chronometer.stop()

            binding.resetButton.isEnabled = false
            binding.startButton.isEnabled = true
            binding.stopButton.isEnabled = true
        }
    }


    // back 키를 두번 눌렀을 때 종료되도록 설정하기
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when(keyCode){
            KeyEvent.KEYCODE_BACK -> {
                // back 키 처음 누른 경우 프로그램이 종료되지 않는다
                if(System.currentTimeMillis() - initTime > 3000) { // 3000ms는 3초이다.
                    Log.d("mobileapp","Back key가 눌렸어요.. 종료하려면 한번 더 누르세요..")
                    initTime = System.currentTimeMillis() // 처음 Back을 누른 시간이 저장

                    Toast.makeText(this, "Back key가 눌렸어요.. 종료하려면 한번 더 누르세요..", Toast.LENGTH_LONG).show()
                    return true // back key 처리는 하지만, 프로그램을 종료하지 않는다.
                }
            }
            KeyEvent.KEYCODE_VOLUME_UP -> Log.d("mobileapp","VOLUME_UP key가 눌렸어요..")
            KeyEvent.KEYCODE_VOLUME_DOWN -> Log.d("mobileapp","VOLUME_DOWN key가 눌렸어요..")
        }
        return super.onKeyDown(keyCode, event)
    }
}