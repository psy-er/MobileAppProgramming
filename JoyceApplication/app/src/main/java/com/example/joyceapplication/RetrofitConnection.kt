package com.example.joyceapplication

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitConnection {
    companion object{
        private const val BASE_URL = "http://apis.data.go.kr/B552584/UlfptcaAlarmInqireSvc/"

        var jsonNetServ : NetworkService
        val jsonRetrofit : Retrofit
            get() = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        init{
            jsonNetServ = jsonRetrofit.create(NetworkService::class.java)
        }
    }
}