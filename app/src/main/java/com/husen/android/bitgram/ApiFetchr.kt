package com.husen.android.bitgram

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.husen.android.bitgram.api.KucoinApi
import com.husen.android.bitgram.api.RamzinexApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.math.RoundingMode
import java.text.DecimalFormat

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

    private fun getDataSourceItem(): HashMap<String, DataSourceItem> {
        return DataSource.dataSourceListMap
    }

    //FIXME: fetch data is so heavy maybe parallel process in viewModel!!!
    fun fetchKucoinBits(): LiveData<List<BitGramItem>> {

        val responseLiveData: MutableLiveData<List<BitGramItem>> = MutableLiveData()

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
                    Log.d(TAG, "Response received")
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
                    val collectedData = collectData(getDataSourceItem(), gramItems)
                    responseLiveData.value = collectedData
                    Log.e(TAG, "${responseLiveData.value}")
                }
            })
        }
        Log.e(TAG, "${responseLiveData.value}")
        return responseLiveData
    }

    fun fetchRamzinexBits(): LiveData<RamzinexItem> {

        val responseLiveData: MutableLiveData<RamzinexItem> = MutableLiveData()

        CoroutineScope(IO).launch {
            val ramzinexRequest = ramzinexApi.fetchBits()

            ramzinexRequest.enqueue(object : Callback<RamzinexResponse> {
                override fun onFailure(call: Call<RamzinexResponse>, t: Throwable) {
                    Log.e(TAG, "Failed to fetch Ramzinex Bits", t)
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
                    val lastData = finance?.lastData

                    responseLiveData.value = lastData
                }
            })
        }
        return responseLiveData
    }

    //collect local and api data
    private fun collectData(dataSourceList: HashMap<String, DataSourceItem>,
                            fetchBits: List<KucoinItem>): List<BitGramItem> {

        val list: MutableList<BitGramItem> = mutableListOf()

        for (gramItem in fetchBits) {
            val dataSourceItem = dataSourceList[gramItem.symbol]

            val bitLogoURL = dataSourceItem?.bitIconUrl

            val bitName = dataSourceItem?.bitName
            val bitSymbol = dataSourceItem?.bitSymbol
            val bitFaName = dataSourceItem?.bitFaName

            val usaPrice = "${gramItem.lastPrice}$"
            val usaPercent = "${
                (calPercent(
                    gramItem.changePrice,
                    gramItem.lastPrice
                ))
            }%"
            list.add(BitGramItem(
                bitLogoURL!!,
                bitName!!,
                bitSymbol!!,
                bitFaName!!,
                usaPrice,
                usaPercent
            ))
        }
        return list
    }
    private fun calPercent(changePrice: String, lastPrice: String): String {

        val initialNum = lastPrice.toDouble() - changePrice.toDouble()

        val percent = ((changePrice.toDouble())/(initialNum))*100
        //Round number to 0.01
        val df = DecimalFormat("#.#")
        df.roundingMode = RoundingMode.CEILING
        val RoundPercent = df.format(percent)

        return RoundPercent
    }
}