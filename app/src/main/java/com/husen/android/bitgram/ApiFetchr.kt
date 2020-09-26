package com.husen.android.bitgram

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.husen.android.bitgram.api.KucoinApi
import com.husen.android.bitgram.api.RamzinexApi
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
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
    private val dataSource: HashMap<String, DataSourceItem>

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

        dataSource = getDataSourceItem()
    }

//    fun fetchCollectedData(): LiveData<List<BitGramItem>> {
//        Log.e(TAG, "fetchCollectedData : $kuCoinBits \n $ramzinexBit")
//        return collectData(dataSource, kuCoinBits, ramzinexBit)
//    }
    fun fetchCollectedData1() = runBlocking {
        val bitGramItem = async ( start = CoroutineStart.LAZY ) {

            val kuCoinBits = fetchKuCoinBits()
            val ramzinexBits = fetchRamzinexBit()
            val dataSourceList = getDataSourceItem()

            collectData(dataSourceList, kuCoinBits, ramzinexBits)
        }
    bitGramItem.await()
    }

    // fetch local bits info
    private fun getDataSourceItem(): HashMap<String, DataSourceItem> {
        return DataSource.dataSourceListMap
    }

    // fetch KuCoin Bits
    private suspend fun fetchKuCoinBits() = runBlocking {

        val kuCoinBits = async (start = CoroutineStart.LAZY) {

            val kucoinRequest = kucoinApi.fetchBits()
            val dataSource = getDataSourceItem()

            fetchKuCoinResponse(kucoinRequest, dataSource)
        }
        Log.e(TAG, "fetchKuCoinBits await: $kuCoinBits")
        kuCoinBits.await()
    }

    // fetch Ramzinex Bit (USDT)
    private suspend fun fetchRamzinexBit() = runBlocking {

        val ramzinexBits = async (start = CoroutineStart.LAZY) {
            val ramzinexRequest = ramzinexApi.fetchBits()
            val dataSource = getDataSourceItem()

            fetchRamzinexResponse(ramzinexRequest, dataSource)
        }
        Log.e(TAG, "fetchRamzinexBit await: $ramzinexBits")
        ramzinexBits.await()
    }
}

private fun fetchKuCoinResponse(request: Call<KucoinResponse>, dataSource: HashMap<String, DataSourceItem>)
        : LiveData<List<KucoinItem>> {

    var kuCoinBits: List<KucoinItem>
    val responseLiveData: MutableLiveData<List<KucoinItem>> = MutableLiveData()

    request.enqueue(object : Callback<KucoinResponse> {
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
            responseLiveData.value = kuCoinBits
            Log.e(TAG, "fetchKuCoinBits 1 $kuCoinBits")
        }
    })
    return responseLiveData
}

private fun fetchRamzinexResponse(request: Call<RamzinexResponse>, dataSource: HashMap<String, DataSourceItem>)
        : LiveData<List<RamzinexItem>> {

    var ramzinexBit: List<RamzinexItem> = emptyList()
    val responseLiveData: MutableLiveData<List<RamzinexItem>> = MutableLiveData()

    // only fetch USDT info from Ramzinex
    request.enqueue(object : Callback<RamzinexResponse> {
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
            responseLiveData.value = ramzinexBit
            Log.e(TAG, "fetchRamzinexBit 1 $ramzinexBit")
        }
    })
    return responseLiveData
}

//collect local and api data
private fun collectData(dataSourceList: HashMap<String, DataSourceItem>,
                        kuCoinBits: List<KucoinItem>,
                        ramzinexBits: List<RamzinexItem>)
        : LiveData<List<BitGramItem>> {

        val LiveListData: MutableLiveData<List<BitGramItem>> = MutableLiveData()
        val list: MutableList<BitGramItem> = mutableListOf()

        for (gramItem in kuCoinBits) {
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

            val usdt = ramzinexBits[0].lastPrice
            val irPrice = (usaPrice.toInt() * usdt.toInt()).toString()
            val irPercent = ramzinexBits[0].changePercent

            list.add(BitGramItem(
                bitLogoURL!!,
                bitName!!,
                bitSymbol!!,
                bitFaName!!,
                usaPrice,
                usaPercent,
                irPrice,
                irPercent
            ))
        }
        // TODO rial to toman & add usdt to list & set percent correctly
        Log.e(TAG, "collectData: $list")
        LiveListData.value = list
        return LiveListData
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