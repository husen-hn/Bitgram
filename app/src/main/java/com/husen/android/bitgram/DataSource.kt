package com.husen.android.bitgram

import android.util.Log

class DataSource {
    companion object {

        private val list = ArrayList<DataSourceItem?>()

        fun getList(): ArrayList<DataSourceItem?> {

            fun addToList(
                bitIdSymbol: String,
                bitName: String,
                bitSymbol: String,
                bitFaName: String,
                bitIconUrl: String
            ) {
                list.add(
                    DataSourceItem(
                        bitIdSymbol,
                        bitName,
                        bitSymbol,
                        bitFaName,
                        bitIconUrl
                    )
                )
            }

            val bitIdSymbol = listOf(
                "BTC-USDT", "ETH-USDT", "LTC-USDT", "ETC-USDT", "BNB-USDT", "EOS-USDT",
                "XLM-USDT", "XRP-USDT", "TRX-USDT")
            val bitNameList = listOf(
                "Bitcoin", "Ethereum", "Litecoin", "Ethereum Classic", "Binance Coin", "Eos",
                "Stellar", "Ripple", "Tron")
            val bitSymbol = listOf(
                "BTC", "ETH", "LTC", "ETC", "BNB", "EOS", "XLM", "XRP", "TRX")
            val bitFaName = listOf(
                "بیتکوین", "اتریوم", "لایت کوین", "اتریوم کلاسیک", "بایننس کوین", "ایاس", "استلار",
                "ریپل", "ترون")
            val bitIconUrl = listOf(
                setIconUrl(bitSymbol[0]), setIconUrl(bitSymbol[1]), setIconUrl(bitSymbol[2]) ,
                setIconUrl(bitSymbol[3]), setIconUrl(bitSymbol[4]), setIconUrl(bitSymbol[5]) ,
                setIconUrl(bitSymbol[6]), setIconUrl(bitSymbol[7]), setIconUrl(bitSymbol[8]))

            for (index in bitIdSymbol.indices) {
                addToList(
                    bitIdSymbol[index],
                    bitNameList[index],
                    bitSymbol[index],
                    bitFaName[index],
                    bitIconUrl[index]
                )
            }
            return list
        }
        private fun setIconUrl(element: String): String {

            return "https://assets-currency.kucoin.com/www/coin/pc/${element}.png"
        }
    }
}