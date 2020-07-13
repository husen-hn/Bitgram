package com.husen.android.bitgram.api

import retrofit2.Call
import retrofit2.http.GET

interface KucoinApi {
    @GET("/")
    fun fetchContents(): Call<String>
}