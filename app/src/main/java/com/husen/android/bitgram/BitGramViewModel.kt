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

                val usaPrice = setCommas(gramItem.lastPrice)
                val usaPercent = calUsPercent(
                        gramItem.changePrice,
                        gramItem.lastPrice )

                val usdt = ramzinexList[0].lastPrice
                val irPrice = setCommas(
                    rialToToman((gramItem.lastPrice.toBigDecimal() * usdt.toBigDecimal()).toString())
                )
                Log.e(TAG, "${ramzinexList[0].changePercent}, $usaPercent")
                val irPercent = calIrPercent(ramzinexList[0].changePercent, usaPercent)
                Log.e(TAG, "irPercent $irPercent, $usaPercent")
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

    private fun rialToToman(rial: String): String{
        val bigDecimalRial = rial.toBigDecimal()
        val roundedRial = "%.0f".format(bigDecimalRial)
        val arrayRial = arrayListOf<String>()

        for (i in roundedRial)
            arrayRial.add(i.toString())

        arrayRial.removeAt(arrayRial.size-1)

        var string = ""
        for (i in arrayRial) {
            string += i
        }
        return string
    }
    //set commas like 12,345,324
    private fun setCommas(price: String): String {

        var priceWithComma = price
        if (priceWithComma.toDouble() > 999) {//don't accept ###.#### nums
            val df = DecimalFormat("#,###,###,###,###")
            priceWithComma = df.format(priceWithComma.toDouble())
        }
        return priceWithComma
    }
    private fun calUsPercent(changePrice: String, lastPrice: String): String {

        val initialNum = lastPrice.toDouble() - changePrice.toDouble()

        val percent = ((changePrice.toDouble()) / (initialNum)) * 100
        //Round number to 0.01
        val df = DecimalFormat("#.#")
        df.roundingMode = RoundingMode.CEILING
        val RoundPercent = df.format(percent)

        return RoundPercent
    }
    private fun calIrPercent(irPercent: String, usPercent: String): String {
        val percent = irPercent.toDouble() + usPercent.toDouble()

        //Round number to 0.01
        val df = DecimalFormat("#.#")
        df.roundingMode = RoundingMode.CEILING
        return df.format(percent)
    }

    init {
        bitGramItemsLiveData = fetchMergedData()
    }
}