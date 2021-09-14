package com.w4eret1ckrtb1tch.app34

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.w4eret1ckrtb1tch.app34.BindingAdapters.setImageUrl
import com.w4eret1ckrtb1tch.app34.data.dto.user.User
import com.w4eret1ckrtb1tch.app34.data.dto.user.UserDto
import com.w4eret1ckrtb1tch.app34.data.dto.user.UsersDto
import com.w4eret1ckrtb1tch.app34.data.source.RetrofitInterface
import com.w4eret1ckrtb1tch.app34.databinding.ActivityMainBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import java.text.DateFormat
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection


class MainActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(MainActivityViewModel::class.java)
    }

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // TODO: 11.09.2021 retrofit

        val interceptor = HttpLoggingInterceptor().apply {
            if(BuildConfig.DEBUG) level = HttpLoggingInterceptor.Level.BASIC
        }

        val okHttpClient1 = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val gsonBuilder = GsonBuilder()
            .enableComplexMapKeySerialization()
            .disableInnerClassSerialization()
            .setDateFormat(DateFormat.LONG)
            .setVersion(1.2)
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://reqres.in/")
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient1)
            .build()
        val service = retrofit.create(RetrofitInterface::class.java)

        val callback = object : retrofit2.Callback<UsersDto> {
            override fun onResponse(
                call: retrofit2.Call<UsersDto>,
                response: retrofit2.Response<UsersDto>
            ) {
                Log.d("TAG", "Users onResponse_retrofit ok: ${response.body()} ")

            }

            override fun onFailure(call: retrofit2.Call<UsersDto>, t: Throwable) {
                Log.d("TAG", "Users onResponse_retrofit err: ${t.printStackTrace()} ")
                t.printStackTrace()
            }

        }

        service.getUsers().enqueue(callback)

        service.getUsers(5).enqueue(callback)

        service.getUser(10)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ userDto ->
                Log.d("TAG", "User RxJava2CallAdapterFactory ok: $userDto")
            }, { error ->
                error.printStackTrace()
            })

        // TODO: 04.09.2021 Повышенная сложность*: распарсите объект при помощи Gson и вставьте полученные данные в верстку.
        viewModel.getUser().observe(this) { user ->
            binding.user.text = user.toString()
            binding.avatar.setImageUrl(user.avatar)
        }

        viewModel.getMovie().observe(this) { movie ->
            binding.user.text = movie.toString()
        }

        binding.search.setOnClickListener {
            val movieId = binding.query.text.toString().toInt()
            viewModel.setMovie(movieId)
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
                val data = gson.fromJson(jsonStr, UserDto::class.java)
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
                    val data1 = gson.fromJson(jsonStr1, UserDto::class.java)
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
                val data2 = gson.fromJson(jsonStr2, UserDto::class.java)
                Log.d("TAG", "user2: ${data2.user}")
            }
        }

    }
}