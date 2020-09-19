package com.husen.android.bitgram

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.husen.android.bitgram.api.KucoinApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.math.RoundingMode
import java.text.DecimalFormat

private const val TAG = "KucoinFetchr"

class KucoinFetchr {
    private val kucoinApi: KucoinApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.kucoin.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        kucoinApi = retrofit.create(KucoinApi::class.java)
    }

    fun getDataSourceItem(): HashMap<String, DataSourceItem> {
        return DataSource.dataSourceListMap
    }

    //FIXME: fetch data is so heavy maybe parallel process in viewModel!!!
    fun fetchBits(): LiveData<List<GramItem>> {

        val responseLiveData: MutableLiveData<List<GramItem>> = MutableLiveData()

        CoroutineScope(IO).launch {
            val kucoinRequest = kucoinApi.fetchBits()

            kucoinRequest.enqueue(object : Callback<KucoinResponse> {
                override fun onFailure(call: Call<KucoinResponse>, t: Throwable) {
                    Log.e(TAG, "Failed to fetch Bits", t)
                }

                override fun onResponse(
                    call: Call<KucoinResponse>,
                    response: Response<KucoinResponse>
                ) {
                    Log.d("XXXX", "Response received")
                    val kucoinResponse = response.body()
                    val bitResponse = kucoinResponse?.bits
                    var gramItems = bitResponse?.gramItems
                        ?: mutableListOf()
                    gramItems = gramItems.filterNot {
                        it.lastPrice.isBlank()
                    }
                    gramItems = gramItems.filter {
                        it.symbol.subSequence(it.symbol.length - 4, it.symbol.length) == "USDT"
                        DataSource.dataSourceListMap.containsKey(it.symbol)
                    }
                    responseLiveData.value = gramItems
                }
            })
        }
        return responseLiveData
    }
}