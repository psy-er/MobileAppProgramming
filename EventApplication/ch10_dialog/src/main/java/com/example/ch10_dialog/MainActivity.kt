package com.example.ch10_dialog

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.ProgressDialog.show
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.DatePicker

import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ch10_dialog.databinding.ActivityMainBinding
import com.example.ch10_dialog.databinding.DialogCustomBinding
import com.google.android.material.navigation.NavigationView

// NavigationView.OnNavigationItemSelectedListener에 관한 부분을 onCreate() 밖에 선언
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var binding : ActivityMainBinding
    lateinit var toggle : ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Drawerlayout과 연결, binding.id(drawer)와 연결, res에 있는 string 불러오기
        toggle = ActionBarDrawerToggle(this, binding.drawer ,R.string.drawer_opened, R.string.drawer_closed )
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 화면에 보여짐
        toggle.syncState()

        // 드로어 레이아웃 아이템이 클릭할 때마다 리스너가 붙여진다
        binding.mainDrawerView.setNavigationItemSelectedListener(this)

        // 각 버튼마다 onclicklistener를 넣어준다
        binding.btnDate.setOnClickListener {
            // month만 0부터 시작한다
            DatePickerDialog(this, object : DatePickerDialog.OnDateSetListener {

                // 다이얼로그에 toast를 띄우기 위해 this 대신 applicationContext 사용해 DataPickerDialog 불러오기
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    Toast.makeText(applicationContext, "$year 년 ${month + 1} 월 $dayOfMonth 일", Toast.LENGTH_LONG).show()
                    binding.btnDate.text = "$year 년 ${month + 1} 월 $dayOfMonth 일"
                    binding.btnDate.textSize = 24f
                    binding.btnDate.setTextColor(Color.parseColor("#00ffff"))
                }
            }, 2024, 3, 3).show() // 4월로 출력
        }


        binding.btnTime.setOnClickListener {
            TimePickerDialog(this, object : TimePickerDialog.OnTimeSetListener {
                override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                    Toast.makeText(applicationContext, "$hourOfDay 시 $minute 분", Toast.LENGTH_LONG).show()
                    binding.btnTime.text = "$hourOfDay 시 $minute 분"
                    binding.btnTime.textSize = 24f
                    binding.btnTime.setTextColor(Color.parseColor("#ffff00"))
                }
            }, 15, 29, true).show()
        }


        // onClickListener 이벤트 핸들러 정의
        val eventHandler = object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    Log.d("mobileapp", "BUTTON_POSITIVE")
                } else if (which == DialogInterface.BUTTON_NEGATIVE) {
                    Log.d("mobileapp", "BUTTON_NEGATIVE")
                }
            }
        }


        // [AlertDialog 알림창 구현]
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

        // [SetItems로 목록 출력 알림창 구현]
        val items = arrayOf<String>("빨강", "노랑", "파랑", "초록")

        binding.btnAlertItem.setOnClickListener {
            AlertDialog.Builder(this).run() {
                setTitle("알림창 - 아이템")
                setIcon(android.R.drawable.ic_dialog_alert)
                setItems(items, object : DialogInterface.OnClickListener {

                    // which : 아이템 선택한 위치 받아오기
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        Log.d("mobileapp", "${items[which]} 선택")
                        binding.btnAlertItem.text = "${items[which]} 선택"
                    }
                })
                // 버튼이 없으면 바로 반영된다.
                setPositiveButton("예", eventHandler)
                setNegativeButton("아니오", eventHandler)
                //setNeutralButtom("상세정보", null)
                show()
            }
        }

        var selected = 0 // 위치값 저장 변수
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

        // [SetSingleChoice로 목록 출력 알림창 구현 => Radio 버튼으로 보임]
        // checkedItem을 1로 설정하여 제품을 하나만 선택하기
        // 선택한 위치 정보 제공
        binding.btnAlertSingle.setOnClickListener {
            AlertDialog.Builder(this).run() {
                setTitle("알림창 - Single")
                setIcon(android.R.drawable.ic_dialog_alert)
                setSingleChoiceItems(items, 1, object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        Log.d("mobileapp", "${items[which]} 선택")
                        selected = which // which 선택 위치
                    }
                })
                // onClick을 눌렀을 때 그 위치를 설정하기 위해 핸들러 작성
                setPositiveButton("예", eventHandler2)
                setNegativeButton("아니오", eventHandler2)
                //setNeutralButton("상세정보", null)
                show()
            }
        }

        // 선택한 위치 정보, 체크 정보 제공
        binding.btnAlertMulti.setOnClickListener {
            AlertDialog.Builder(this).run() {
                setTitle("알림창 - 다수 선택")
                setIcon(android.R.drawable.ic_dialog_alert)
                // 다중 선택 배열은 booleanArray로 준다.
                setMultiChoiceItems(items, booleanArrayOf(false, true, true, false), object:DialogInterface.OnMultiChoiceClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int, isChecked: Boolean) {
                        Log.d("mobileapp", "${items[which]} ${if(isChecked) "선택" else "해제"}")
                    }
                })
                setPositiveButton("예", eventHandler)
                setNegativeButton("아니오", eventHandler)
                show()
            }
        }

        // 사용자 지정 다이얼로그 -> layout/diaglog_custom 파일 사용
        // DialogCustomBinding 파일 자동 생성
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
                    else if(dialogBinding.rbtn3.isChecked){
                        binding.btnAlertCustom.text = dialogBinding.rbtn3.text.toString()
                    }
                    else if(dialogBinding.rbtn4.isChecked){
                        binding.btnAlertCustom.text = dialogBinding.rbtn4.text.toString()
                    }
                } else if (which == DialogInterface.BUTTON_NEGATIVE) {
                    Log.d("mobileapp", "BUTTON_NEGATIVE")
                }
            }
        }

        val customDlg = AlertDialog.Builder(this).run() {
            setTitle("알림창 - 사용자 화면")
            setIcon(android.R.drawable.ic_dialog_alert)
            setView(dialogBinding.root)

            setPositiveButton("예", eventHandler3) // 이벤트핸들러 버튼 처리
            setNegativeButton("아니오", eventHandler3)
            create()// 생성만 하고
        }
        binding.btnAlertCustom.setOnClickListener {
            customDlg.show()// 보여줌
        }
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
            R.id.item_call -> {
                //og.d("mobileapp", "Navigation Menu : 메뉴 4")
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:02-911"))
                startActivity(intent)
                true
            }
            R.id.item_mail -> {
                val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:seyounpark@duksung.ac.kr"))
                startActivity(intent)
                true
            }

        }
        return false
    }

    // Option Menu
    // onCreateOptionMenu는 미리 만들어 놓은 메뉴를 코틀린이 사용할 수 있게 만듦
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // 메뉴 화면 구성 가지고 옴
        // res/menu/menu.navigation을 가지고 와 menu에 저장
        menuInflater.inflate(R.menu.menu_navigation, menu)

        // 여러가지 액션뷰 중에 서치뷰 지정
        val searchView = menu?.findItem(R.id.menu_search)?.actionView as SearchView // null 가능, as 캐스트

        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
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
        }
        return super.onOptionsItemSelected(item)
    }
}