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

    private fun getDataSourceItem(): HashMap<String, DataSourceItem> {
        return DataSource.dataSourceListMap
    }

    //FIXME: fetch data is so heavy maybe parallel process in viewModel!!!
    fun fetchBits(): LiveData<List<BitGramItem>> {

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

    //collect local and api data
    private fun collectData(dataSourceList: HashMap<String, DataSourceItem>,
                            fetchBits: List<GramItem>): List<BitGramItem> {

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