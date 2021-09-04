package com.w4eret1ckrtb1tch.app34

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.w4eret1ckrtb1tch.app34.domain.Data
import com.w4eret1ckrtb1tch.app34.domain.User
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.Executors

class MainActivityViewModel : ViewModel() {

    private val user: MutableLiveData<User> = MutableLiveData()
    private val gson = Gson()
    private val okHttpClient = OkHttpClient()

    fun getUser(): LiveData<User> = user

    init {
        setUser()
    }

    private fun setUser() {
        val requestGet = Request.Builder()
            .url("https://reqres.in/api/users/3")
            .build()
        Executors.newSingleThreadExecutor().execute {
            okHttpClient.newCall(requestGet).execute().use { response ->
                val jsonStr = response.body?.string()
                Log.d("TAG", "data2: $jsonStr")
                val data = gson.fromJson(jsonStr, Data::class.java)
                Log.d("TAG", "user2: ${data.user}")
                user.postValue(data.user)
            }
        }
    }

}