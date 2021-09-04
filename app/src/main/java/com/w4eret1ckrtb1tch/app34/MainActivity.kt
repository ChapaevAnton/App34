package com.w4eret1ckrtb1tch.app34

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.w4eret1ckrtb1tch.app34.databinding.ActivityMainBinding
import com.w4eret1ckrtb1tch.app34.domain.User
import com.w4eret1ckrtb1tch.app34.domain.Data
import okhttp3.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.Exception
import java.net.URL
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection


class MainActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(MainActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // TODO: 04.09.2021 Повышенная сложность*: распарсите объект при помощи Gson и вставьте полученные данные в верстку.
        viewModel.getUser().observe(this) { user ->
            binding.user.text = user.toString()
        }


        // TODO: 04.09.2021 JSON Gson
        val gson = Gson()
        val user = User(1, "test@test.com", "Ivanov", "Ivan", "https://test.com/avatar.jpg")

        val inputGsonValue = gson.toJson(user)
        Log.d("TAG", "inputGsonValue: $inputGsonValue")

        val outputGsonValue = gson.fromJson(inputGsonValue, User::class.java)
        Log.d("TAG", "outputGsonValue: $outputGsonValue")


        // TODO: 04.09.2021 HttpURLConnection 
        Executors.newSingleThreadExecutor().execute {
            val url = URL("https://reqres.in/api/users/1")
            val connection = url.openConnection() as HttpsURLConnection

            try {
                val input = BufferedReader(InputStreamReader(connection.inputStream))
                val jsonStr = input.readLine()
                Log.d("TAG", "data: $jsonStr ")
                val data = gson.fromJson(jsonStr, Data::class.java)
                Log.d("TAG", "user: ${data.user}")
            } finally {
                connection.disconnect()
            }
        }

        // TODO: 04.09.2021 OkHttp

        val okHttpClient = OkHttpClient()
        val requestGet = Request.Builder()
            .url("https://reqres.in/api/users/2")
            .build()

        okHttpClient.newCall(requestGet).enqueue(object : Callback {
            override fun onFailure(call: Call, error: IOException) {
                error.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val jsonStr1 = response.body?.string()
                    Log.d("TAG", "data1: $jsonStr1")
                    val data1 = gson.fromJson(jsonStr1, Data::class.java)
                    Log.d("TAG", "user1: ${data1.user}")
                } catch (error: Exception) {
                    error.printStackTrace()
                    Log.d("TAG", "response: $response")
                }
            }
        })

        val requestGet1 = Request.Builder()
            .url("https://reqres.in/api/users/3")
            .build()

        Executors.newSingleThreadExecutor().execute {
            okHttpClient.newCall(requestGet1).execute().use { response ->
                val jsonStr2 = response.body?.string()
                Log.d("TAG", "data2: $jsonStr2")
                val data2 = gson.fromJson(jsonStr2, Data::class.java)
                Log.d("TAG", "user2: ${data2.user}")
            }
        }

    }
}