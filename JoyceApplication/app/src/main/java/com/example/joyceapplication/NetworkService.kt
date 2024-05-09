package com.example.joyceapplication

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// 네트워크서비스는 기본 주소 이후의 파라미터 쿼리에 대한 부분을 어떻게 만들어 줄 것인지 정하는 인터페이스이다.
// http://apis.data.go.kr/B552584/UlfptcaAlarmInqireSvc/getUlfptcaAlarmInfo?year=2024&pageNo=1&numOfRows=10&returnType=xml&serviceKey=
interface NetworkService {

    @GET("getUlfptcaAlarmInfo")
    fun getJsonList(
        @Query("year") year:Int,
        @Query("pageNo") pageNo:Int,
        @Query("numOfRows") numOfRows: Int,
        @Query("returnType") returnType:String,
        @Query("serviceKey") serviceKey:String
    ) : Call<JsonResponse>
}