package com.husen.android.bitgram

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.husen.android.bitgram.api.KucoinApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val TAG = "KucoinFetchr"

class KucoinFetchr {
    private val kucoinApi: KucoinApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.kucoin.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

        kucoinApi = retrofit.create(KucoinApi::class.java)
    }

    fun fetchContents(): LiveData<String> {
        val responseLiveData: MutableLiveData<String> = MutableLiveData()
        val kucoinRequest = kucoinApi.fetchContents()

        kucoinRequest.enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e(TAG, "Failed to fetch Bits", t)
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.d(TAG, "Response received")
                responseLiveData.value = response.body()
            }
        })
    return responseLiveData
    }
}