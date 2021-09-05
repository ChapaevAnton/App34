package com.w4eret1ckrtb1tch.app34

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.w4eret1ckrtb1tch.app34.api.TmdbAPI
import com.w4eret1ckrtb1tch.app34.domain.movie.Movie
import com.w4eret1ckrtb1tch.app34.domain.user.Data
import com.w4eret1ckrtb1tch.app34.domain.user.User
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.Executors

class MainActivityViewModel : ViewModel() {

    private val user: MutableLiveData<User> = MutableLiveData()
    private val movie: MutableLiveData<Movie> = MutableLiveData()
    private val gson = Gson()
    private val okHttpClient = OkHttpClient()

    fun getUser(): LiveData<User> = user
    fun getMovie(): LiveData<Movie> = movie

    init {
        setUser()
    }

    private fun setUser() {
        Executors.newSingleThreadExecutor().execute {
            val requestGet = Request.Builder()
                .url("https://reqres.in/api/users/3")
                .build()
            okHttpClient.newCall(requestGet).execute().use { response ->
                val jsonStr = response.body?.string()
                Log.d("TAG", "data2: $jsonStr")
                val data = gson.fromJson(jsonStr, Data::class.java)
                Log.d("TAG", "user2: ${data.user}")
                user.postValue(data.user)
            }
        }
    }

    fun setMovie(movieId: Int) {
        Executors.newSingleThreadExecutor().execute {
            val requestGet = Request.Builder()
                .url("https://api.themoviedb.org/3/movie/$movieId?api_key=${TmdbAPI.API_KEY_V3}&language=ru-RU")
                .build()
            okHttpClient.newCall(requestGet).execute().use { response ->
                val resultStr = response.body?.string()
                val dataMovie: Movie? = gson.fromJson(resultStr, Movie::class.java)
                Log.d("TAG", "movie: $dataMovie ")
                movie.postValue(dataMovie)
            }
        }
    }

}