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
    var initTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 기본 코틀린 코드
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // 위아래 같은 방식임
        //setContentView(R.layout.activity_main)

        var prevTime = 0L

        binding.startButton.text = "시작"
        binding.startButton.textSize = 24.0f

        // 코틀린에서도 위젯의 성질을 변화시킬 수 있다.
        binding.startButton.setOnClickListener {
            binding.chronometer.base = SystemClock.elapsedRealtime() + prevTime
            binding.chronometer.start()

            binding.startButton.isEnabled = false
            binding.stopButton.isEnabled = true // ??
            binding.resetButton.isEnabled = true
        }
        binding.stopButton.setOnClickListener{
            prevTime = binding.chronometer.base - SystemClock.elapsedRealtime() // start~stop 시간 저장
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

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when(keyCode){
            KeyEvent.KEYCODE_BACK -> {
                if(System.currentTimeMillis() - initTime > 3000) { // 3000ms는 3초이다.
                    Log.d("mobileapp","Back key가 눌렸어요.. 종료하려면 한번 더 누르세요..")
                    initTime = System.currentTimeMillis() // 처음 Back을 누른 시간이 저장

                    Toast.makeText(this, "Back key가 눌렸어요.. 종료하려면 한번 더 누르세요..", Toast.LENGTH_LONG).show()
                    return true
                }
            }
            KeyEvent.KEYCODE_VOLUME_UP -> Log.d("mobileapp","VOLUME_UP key가 눌렸어요..")
            KeyEvent.KEYCODE_VOLUME_DOWN -> Log.d("mobileapp","VOLUME_DOWN key가 눌렸어요..")
        }
        return super.onKeyDown(keyCode, event)
    }
}