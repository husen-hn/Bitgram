package com.husen.android.bitgram.Network.KucoinApi

import com.husen.android.bitgram.KucoinResponse
import retrofit2.Call
import retrofit2.http.GET

interface KucoinApi {
    @GET("api/v1/market/allTickers")
    fun fetchBits(): Call<KucoinResponse>
}