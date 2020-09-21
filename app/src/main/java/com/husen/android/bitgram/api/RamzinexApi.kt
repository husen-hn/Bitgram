package com.husen.android.bitgram.api

import com.husen.android.bitgram.RamzinexResponse
import retrofit2.Call
import retrofit2.http.GET

interface RamzinexApi {
    @GET("exchange/api/exchange/prices")
    fun fetchBits(): Call<RamzinexResponse>
}