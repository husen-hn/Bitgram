package com.husen.android.bitgram

import android.util.Log
import androidx.lifecycle.*
import java.math.RoundingMode
import java.text.DecimalFormat

private const val TAG = "BitGramViewModel"
class BitGramViewModel : ViewModel() {

    var bitGramItemsLiveData: LiveData<List<BitGramItem>>

    private fun fetchKuCoin(): LiveData<List<KucoinItem>> {
        return ApiFetchr().fetchKuCoinBits()
    }

    private fun fetchRamzinex(): LiveData<List<RamzinexItem>> {
        return ApiFetchr().fetchRamzinexBit()
    }

    private fun fetchMergedData(): LiveData<List<BitGramItem>> {

        val kuCoinBits = fetchKuCoin()
        val ramzinexBits = fetchRamzinex()
        val dataSourceList = ApiFetchr().getDataSourceItem()

        val result = MediatorLiveData<List<BitGramItem>>()
        result.addSource(kuCoinBits) { _ ->
            result.value = combineLatestData(kuCoinBits, ramzinexBits, dataSourceList)
        }
        result.addSource(ramzinexBits) { _ ->
            result.value = combineLatestData(kuCoinBits, ramzinexBits, dataSourceList)
        }

        Log.e(TAG, "result: $result")
        return result
    }

    private fun combineLatestData(kuCoinBits: LiveData<List<KucoinItem>>,
                                  ramzinexBits: LiveData<List<RamzinexItem>>,
                                  dataSourceList: HashMap<String, DataSourceItem>)
            : List<BitGramItem>? {

        val kuCoinList = kuCoinBits.value
        val ramzinexList = ramzinexBits.value

        val list: MutableList<BitGramItem> = mutableListOf()

        if (!kuCoinList.isNullOrEmpty() && !ramzinexList.isNullOrEmpty()){
            for (gramItem in kuCoinList) {
                val dataSourceItem = dataSourceList[gramItem.symbol]

                val bitLogoURL = dataSourceItem?.bitIconUrl

                val bitName = dataSourceItem?.bitName
                val bitSymbol = dataSourceItem?.bitSymbol
                val bitFaName = dataSourceItem?.bitFaName

                val usaPrice = gramItem.lastPrice
                val usaPercent = calPercent(
                        gramItem.changePrice,
                        gramItem.lastPrice )

                val usdt = ramzinexList[0].lastPrice
                val irPrice = (usaPrice.toFloat() * usdt.toFloat()).toString()
                val irPercent = ramzinexList[0].changePercent
                //TODO rial to toman
                list.add(
                    BitGramItem(
                        bitLogoURL!!,
                        bitName!!,
                        bitSymbol!!,
                        bitFaName!!,
                        usaPrice,
                        usaPercent,
                        irPrice,
                        irPercent
                    )
                )
            }
        }
        return list
    }
    private fun calPercent(changePrice: String, lastPrice: String): String {

        val initialNum = lastPrice.toDouble() - changePrice.toDouble()

        val percent = ((changePrice.toDouble()) / (initialNum)) * 100
        //Round number to 0.01
        val df = DecimalFormat("#.#")
        df.roundingMode = RoundingMode.CEILING
        val RoundPercent = df.format(percent)

        return RoundPercent
    }

    init {
        bitGramItemsLiveData = fetchMergedData()
    }
}