package com.husen.android.bitgram.Models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.husen.android.bitgram.DataSource
import com.husen.android.bitgram.Models.DataSourceItem
import com.husen.android.bitgram.Models.KucoinItem
import com.husen.android.bitgram.Models.RamzinexItem
import com.husen.android.bitgram.Network.KucoinApi
import com.husen.android.bitgram.Network.RamzinexApi
import com.husen.android.bitgram.Response.KucoinResponse
import com.husen.android.bitgram.Response.RamzinexResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "KucoinFetchr"

class ApiFetchr {
    private val kucoinApi: KucoinApi
    private val ramzinexApi: RamzinexApi

    init {
        val kucoinRetrofit = Retrofit.Builder()
            .baseUrl("https://api.kucoin.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        kucoinApi = kucoinRetrofit.create(KucoinApi::class.java)

        val ramzinexRetrofit = Retrofit.Builder()
            .baseUrl("https://ramzinex.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        ramzinexApi = ramzinexRetrofit.create(RamzinexApi::class.java)
    }

    // fetch local bits info
    fun getDataSourceItem(): HashMap<String, DataSourceItem> {
        return DataSource.dataSourceListMap
    }

    // fetch KuCoin Bits
    fun fetchKuCoinBits(): LiveData<List<KucoinItem>> {

        val kucoinRequest = kucoinApi.fetchBits()

        var kuCoinBits: List<KucoinItem>
        val responseLiveData: MutableLiveData<List<KucoinItem>> = MutableLiveData()
        val dataSource = getDataSourceItem()

        kucoinRequest.enqueue(object : Callback<KucoinResponse> {
            override fun onFailure(call: Call<KucoinResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch KuCoin Bits", t)
            }

            override fun onResponse(
                call: Call<KucoinResponse>,
                response: Response<KucoinResponse>
            ) {
                Log.d(TAG, "Response received")
                val kucoinResponse = response.body()
                val bitResponse = kucoinResponse?.bits
                kuCoinBits = bitResponse?.gramItems
                    ?: mutableListOf()
                kuCoinBits = kuCoinBits.filterNot {
                    it.lastPrice.isBlank()
                }
                kuCoinBits = kuCoinBits.filter {
                    it.symbol.subSequence(it.symbol.length - 4, it.symbol.length) == "USDT"
                    dataSource.containsKey(it.symbol)
                }
                Log.e(TAG, "fetchKuCoinBit $kuCoinBits")
                responseLiveData.value = kuCoinBits
            }
        })
        return responseLiveData
    }

    // fetch Ramzinex Bit (USDT)
    fun fetchRamzinexBit(): LiveData<List<RamzinexItem>> {

        val ramzinexRequest = ramzinexApi.fetchBits()

        var ramzinexBit: List<RamzinexItem> = emptyList()
        val responseLiveData: MutableLiveData<List<RamzinexItem>> = MutableLiveData()
        val dataSource = getDataSourceItem()

        // only fetch USDT info from Ramzinex
        ramzinexRequest.enqueue(object : Callback<RamzinexResponse> {
            override fun onFailure(call: Call<RamzinexResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch Ramzinex Bit", t)
            }

            override fun onResponse(
                call: Call<RamzinexResponse>,
                response: Response<RamzinexResponse>
            ) {
                Log.d(TAG, "Ramzinex: Response received")
                val ramzinexResponse = response.body()
                val bitResponse = ramzinexResponse?.bits
                val usdt = bitResponse?.usdt
                val finance = usdt?.financialData
                ramzinexBit = listOf(finance!!.lastData)
                    ?: mutableListOf()
                ramzinexBit = ramzinexBit.filterNot {
                    it.lastPrice.isBlank()
                }
                ramzinexBit = ramzinexBit.filter {
                    dataSource.containsKey(it.symbol)
                }
                Log.e(TAG, "fetchRamzinexBit $ramzinexBit")
                responseLiveData.value = ramzinexBit
            }
        })
        return responseLiveData
    }
}