package com.example.ch10_dialog

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.ProgressDialog.show
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ch10_dialog.databinding.ActivityMainBinding
import com.example.ch10_dialog.databinding.DialogCustomBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 추후 추가
        binding.btnDate.setOnClickListener {
            // month만 0부터 시작한다
            DatePickerDialog(this, object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    Toast.makeText(
                        applicationContext,
                        "$year 년 ${month + 1} 월 $dayOfMonth 일",
                        Toast.LENGTH_LONG
                    ).show()
                    binding.btnDate.text = "$year 년 ${month + 1} 월 $dayOfMonth 일"
                    binding.btnDate.textSize = 24f
                    binding.btnDate.setTextColor(Color.parseColor("#00ffff"))
                }
            }, 2024, 3, 3).show()
        }
        binding.btnTime.setOnClickListener {
            TimePickerDialog(this, object : TimePickerDialog.OnTimeSetListener {
                override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                    Toast.makeText(applicationContext, "$hourOfDay 시 $minute 분", Toast.LENGTH_LONG)
                        .show()
                    binding.btnTime.text = "$hourOfDay 시 $minute 분"
                    binding.btnDate.textSize = 24f
                    binding.btnDate.setTextColor(Color.parseColor("#ffff00"))
                }

            }, 15, 29, true).show()
        }
        val eventHandler = object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    Log.d("mobileapp", "BUTTON_POSITIVE")
                } else if (which == DialogInterface.BUTTON_NEGATIVE) {
                    Log.d("mobileapp", "BUTTON_NEGATIVE")
                }
            }
        }
        binding.btnAlert.setOnClickListener {
            AlertDialog.Builder(this).run() {
                setTitle("알림창 - 모앱")
                setIcon(android.R.drawable.ic_dialog_alert)
                setMessage("정말 종료하시겠습니까?")
                setPositiveButton("예", eventHandler)
                setNegativeButton("아니오", eventHandler)
                //setNeutralButtom("상세정보", null)
                show()
            }
        }

        val items = arrayOf<String>("빨강", "노랑", "파랑", "초록")
        binding.btnAlertItem.setOnClickListener {
            AlertDialog.Builder(this).run() {
                setTitle("알림창 - 아이템")
                setIcon(android.R.drawable.ic_dialog_alert)
                setItems(items, object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        Log.d("mobileapp", "${items[which]} 선택")
                        binding.btnAlertItem.text = "${items[which]} 선택"
                    }
                })
                setPositiveButton("예", eventHandler)
                setNegativeButton("아니오", eventHandler)
                //setNeutralButtom("상세정보", null)
                show()
            }
        }

            var selected = 0
            val eventHandler2 = object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    if (which == DialogInterface.BUTTON_POSITIVE) {
                        Log.d("mobileapp", "BUTTON_POSITIVE")
                        binding.btnAlertSingle.text = "${items[selected]} 선택"
                    } else if (which == DialogInterface.BUTTON_NEGATIVE) {
                        Log.d("mobileapp", "BUTTON_NEGATIVE")
                    }
                }
            }

            binding.btnAlertSingle.setOnClickListener {
                AlertDialog.Builder(this).run() {
                    setTitle("알림창 - Single")
                    setIcon(android.R.drawable.ic_dialog_alert)
                    setSingleChoiceItems(items, 1, object : DialogInterface.OnClickListener {

                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            Log.d("mobileapp", "${items[which]} 선택")
                            selected = which
                        }
                    })
                    setPositiveButton("예", eventHandler2)
                    setNegativeButton("아니오", eventHandler2)
                    //setNeutralButton("상세정보", null)
                    show()
                }
            }
            binding.btnAlertMulti.setOnClickListener {
                AlertDialog.Builder(this).run() {
                    setTitle("알림창 - 다수 선택")
                    setIcon(android.R.drawable.ic_dialog_alert)

                    setMultiChoiceItems(items, booleanArrayOf(false, true, true, false), object:DialogInterface.OnMultiChoiceClickListener{
                        override fun onClick(
                            dialog: DialogInterface?,
                            which: Int,
                            isChecked: Boolean
                        ) {
                            Log.d("mobileapp", "${items[which]} ${if(isChecked) "선택" else "해제"}")
                        }
                    })

                    setPositiveButton("예", eventHandler)
                    setNegativeButton("아니오", eventHandler)
                    show()
                }
            }


            val dialogBinding = DialogCustomBinding.inflate(layoutInflater)
            val eventHandler3 = object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    if (which == DialogInterface.BUTTON_POSITIVE) {
                        Log.d("mobileapp", "BUTTON_POSITIVE")
                        if(dialogBinding.rbtn1.isChecked){
                            binding.btnAlertCustom.text = dialogBinding.rbtn1.text.toString()
                        }
                        else if(dialogBinding.rbtn2.isChecked){
                            binding.btnAlertCustom.text = dialogBinding.rbtn2.text.toString()
                        }
                    } else if (which == DialogInterface.BUTTON_NEGATIVE) {
                        Log.d("mobileapp", "BUTTON_NEGATIVE")
                    }
                }
            }

            binding.btnAlertCustom.setOnClickListener {
                AlertDialog.Builder(this).run() {
                    setTitle("알림창 - 사용자 화면")
                    setIcon(android.R.drawable.ic_dialog_alert)

                    setView(dialogBinding.root)

                    setPositiveButton("예", eventHandler3)
                    setNegativeButton("아니오", eventHandler3)
                    show()
                }
            }
        }
    // 수업이랑 }} 개수가 다르다.. 확인해보기
    // Option Menu

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_navigation, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.item1 -> {
                Log.d("mobileapp", "Option Menu : 메뉴 1")
                binding.btnDate.setTextColor(Color.parseColor("#ffff00"))
                true
            }
            R.id.item2 -> true
            R.id.item3 -> true
            R.id.item4 -> true
        }
        return super.onOptionsItemSelected(item)
    }
}