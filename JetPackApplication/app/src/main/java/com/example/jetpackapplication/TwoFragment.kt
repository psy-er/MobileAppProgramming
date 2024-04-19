package com.example.jetpackapplication

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpackapplication.databinding.FragmentTwoBinding
import com.example.jetpackapplication.databinding.ItemRecyclerviewBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

// [필수] Adapter와 ViewHolder 클래스 작성하기
// MyViewHolder를 생성해서 item_recyclerview에 있는 layout을 가지고 온다.
class MyViewHolder(val binding:ItemRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root)
class MyAdapter(val datas:MutableList<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyViewHolder).binding
        binding.itemData.text = datas[position]
    }

    override fun getItemCount(): Int {
        return datas.size
    }
}

// 필수 오버라이드 함수는 없다
class MyItemDecoration(val context: Context):RecyclerView.ItemDecoration(){
    // 꾸미고 배치
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        // 그림 -> 항목
        super.onDraw(c, parent, state)
        c.drawBitmap(BitmapFactory.decodeResource(context.resources, R.drawable.kbo),
            0f, 0f, null)
    }

    // 배치 후 꾸미기
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        // 항목 -> 그림
        super.onDrawOver(c, parent, state)
        c.drawBitmap(BitmapFactory.decodeResource(context.resources, R.drawable.kbo),
            500f, 500f, null)
    }

    // 각각의 item에 대해서 꾸며준다. 근데 recyclerview에서 바로 변경 가능한 부분.
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        view.setBackgroundColor(Color.parseColor("#123456"))
    }
}

/**
 * A simple [Fragment] subclass.
 * Use the [TwoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TwoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // 바인딩 할 layout이름+Binding
        val binding = FragmentTwoBinding.inflate(inflater, container, false)

        // 리스트 값이 변경 될 수 있게 mutableList를 사용
        var datas = mutableListOf<String>()
        for(i in 1..10){
            datas.add("Item $i")
        }

        // [필수] adpter & viewHolder, MyAdapter 클래스 만든다.
        var adapter = MyAdapter(datas)
        binding.recyclerView.adapter = adapter

        // [필수] layoutManager를 이용해 가로로 배치
        var linearlayout = LinearLayoutManager(activity)
        linearlayout.orientation = LinearLayoutManager.HORIZONTAL

        // 여러 줄에 배치하고 싶을 때
        var gridlayout = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = gridlayout //linearlayout

        // 리사이클러 뷰 꾸미기 선택적
        binding.recyclerView.addItemDecoration(MyItemDecoration(activity as Context))

        // 플로팅 버튼
        binding.mainFab.setOnClickListener{
            datas.add("Add Item")
            adapter.notifyDataSetChanged() // adapter 데이터 변경 알려줌
        }
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TwoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TwoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}