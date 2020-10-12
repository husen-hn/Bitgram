package com.husen.android.bitgram

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.launch

private const val TAG = "DataSource"

class DataSource {
    companion object {

        var dataSourceListMap = HashMap<String, DataSourceItem>()

        private fun addToList(
            bitIdSymbol: String,
            bitName: String,
            bitSymbol: String,
            bitFaName: String,
            bitIconUrl: String
        ) {
            dataSourceListMap.put(
                bitIdSymbol,
                DataSourceItem(
                    bitIdSymbol,
                    bitName,
                    bitSymbol,
                    bitFaName,
                    bitIconUrl
                )
            )
        }

        init {
            CoroutineScope(Default).launch {
                val bitIdSymbol = listOf(
                    "BTC-USDT", "ETH-USDT", "LTC-USDT", "ETC-USDT", "BNB-USDT", "EOS-USDT",
                    "XLM-USDT", "XRP-USDT", "TRX-USDT", "KCS-USDT", "LUNA-USDT", "ATOM-USDT",
                    "BSV-USDT", "XTZ-USDT", "ADA-USDT", "SENSO-USDT", "BCHABC-USDT", "VET-USDT",
                    "SUTER-USDT", "ZEC-USDT", "POL-USDT", "DASH-USDT", "ONT-USDT", "DGB-USDT",
                    "CRO-USDT", "NEO-USDT", "XEM-USDT", "USDT-USDT" )
                val bitNameList = listOf(
                    "Bitcoin", "Ethereum", "Litecoin", "Ethereum Classic", "Binance Coin", "Eos",
                    "Stellar", "Ripple", "Tron", "KuCoin Shares", "Terra", "Cosmos", "Bitcoin SV",
                    "Tezos", "Cardano", "Sensorium", "Bitcoin ABC", "VeChain", "suterusu",
                    "Zcash", "POL", "Dash", "Ontology", "DigiByte", "Crypto.com Coin", "Neo", "NEM",
                    "Tether")
                val bitSymbol = listOf(
                    "BTC", "ETH", "LTC", "ETC", "BNB", "EOS", "XLM", "XRP", "TRX", "KCS", "LUNA",
                    "ATOM", "BSV", "XTZ", "ADA", "SENSO", "BCHABC", "VET", "SUTER", "ZEC", "Mining POL",
                    "DASH", "ONT", "DGB", "CRO", "NEO", "XEM", "USDT")
                val bitFaName = listOf(
                    "بیتکوین", "اتریوم", "لایت کوین", "اتریوم کلاسیک", "بایننس کوین", "ایاس", "استلار",
                    "ریپل", "ترون", "کوکوین شیرز", "ترا", "کاسموس", "بیتکوین اس وی", "تزوس", "کاردانو",
                    "سنسوریوم", "بیتکوین ای بی سی", "وچین", "ستیروسو", "زی کش", "پل", "دش", "آنتلوجی"
                    , "دیجی بایت", "کوین Crypto.com", "نئو", "نم", "تتر")
                val bitIconUrl = listOf(
                    setIconUrl(bitSymbol[0]), setIconUrl(bitSymbol[1]), setIconUrl(bitSymbol[2]) ,
                    setIconUrl(bitSymbol[3]), setIconUrl(bitSymbol[4]), setIconUrl(bitSymbol[5]) ,
                    setIconUrl(bitSymbol[6]), setIconUrl(bitSymbol[7]), setIconUrl(bitSymbol[8]),
                    setIconUrl(bitSymbol[9]), "https://assets-currency.kucoin.com/5d6558dd38300c2b63f7ecdd_logo-p2.png",
                    "https://assets-currency.kucoin.com/5ce7b35e38300c4320a4fa03_Cosmos_token.png",
                    setIconUrl("BCHSV"), "https://assets-currency.kucoin.com/5d15789a38300c4320a574e1_Tezos-%28XTZ%29-token-logo-.png",
                    "https://assets-currency.kucoin.com/5d1b3314c29cc606c485e20f_Cardano%EF%BC%88ADA-token-logo-.png",
                    "https://assets-currency.kucoin.com/5e736e355649690008437ef8_47F14068-A607-4FDE-AEF5-A3DC9DC805D2.png",
                    setIconUrl(bitSymbol[16]), setIconUrl(bitSymbol[17]), "https://assets-currency.kucoin.com/5e54d479f816040008d34fb0_4841.png",
                    "https://assets-currency.kucoin.com/5d1b330ac29cc606c485e20e_Zcash-%28ZEC%29-token-logo-.png",
                    "https://assets-currency.kucoin.com/5e903e3ff816040008d3c572_POL_token11111.png", setIconUrl(bitSymbol[21]),
                    setIconUrl(bitSymbol[22]), setIconUrl(bitSymbol[23]), "https://assets-currency.kucoin.com/5cb08e9238300c4320a45e5a_Crypto.com%20Chain_token.png",
                    "https://assets-currency.kucoin.com/5e2128172c0a950008b9b5ef_NEO-token-logo-.png",
                    "https://assets-currency.kucoin.com/5d11efc8134ab772a9abdb39_Nem-Logo-Transparent-Border-.png",
                    "https://assets-currency.kucoin.com/www/coin/pc/USDT.png"
                )

                for (index in bitIdSymbol.indices) {
                    addToList(
                        bitIdSymbol[index],
                        bitNameList[index],
                        bitSymbol[index],
                        bitFaName[index],
                        bitIconUrl[index]
                    )
                }
                Log.d(TAG, "set DataSource")
            }
        }
        private fun setIconUrl(element: String): String {
            return "https://assets-currency.kucoin.com/www/coin/pc/${element}.png"
        }
    }
}