package com.husen.android.bitgram.interfaces

import com.husen.android.bitgram.response.RamzinexResponse
import retrofit2.Call
import retrofit2.http.GET

interface RamzinexApi {
    @GET("exchange/api/exchange/prices")
    fun fetchBits(): Call<RamzinexResponse>
}