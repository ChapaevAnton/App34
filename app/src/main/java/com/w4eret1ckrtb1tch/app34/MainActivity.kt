package com.w4eret1ckrtb1tch.app34

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.w4eret1ckrtb1tch.app34.domain.User

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gson = Gson()
        val user = User(1, "test@test.com", "Ivanov", "Ivan", "https://test.com/avatar.jpg")

        val inputGsonValue = gson.toJson(user)
        Log.d("TAG", "inputGsonValue: $inputGsonValue")

        val outputGsonValue = gson.fromJson(inputGsonValue,User::class.java)
        Log.d("TAG", "outputGsonValue: $outputGsonValue")
    }
}