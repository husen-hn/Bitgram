package com.husen.android.bitgram.interfaces

import com.husen.android.bitgram.response.KucoinResponse
import retrofit2.Call
import retrofit2.http.GET

interface KucoinApi {
    @GET("api/v1/market/allTickers")
    fun fetchBits(): Call<KucoinResponse>
}