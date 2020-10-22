package com.husen.android.bitgram.Network

import com.husen.android.bitgram.Response.KucoinResponse
import retrofit2.Call
import retrofit2.http.GET

interface KucoinApi {
    @GET("api/v1/market/allTickers")
    fun fetchBits(): Call<KucoinResponse>
}