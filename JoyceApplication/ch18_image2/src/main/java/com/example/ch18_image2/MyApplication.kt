package com.example.ch18_image2

import androidx.multidex.MultiDexApplication
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

// Dex : Dalvic Executable (64K) 까지만 가능하다.
class MyApplication : MultiDexApplication() {
    companion object{
        lateinit var auth : FirebaseAuth
        var email:String? = null

        fun checkAuth(): Boolean{
            var currentUser = auth.currentUser
            if(currentUser != null){
                email = currentUser.email
                return currentUser.isEmailVerified
            }
            return false
        }
    }
    override fun onCreate(){
        super.onCreate()
        auth = Firebase.auth
    }
}