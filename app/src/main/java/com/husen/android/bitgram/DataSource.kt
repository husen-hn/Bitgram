package com.husen.android.bitgram

class DataSource {
    companion object {

        private val list = ArrayList<DataSourceItem?>()

        fun getList(): ArrayList<DataSourceItem?> {

            fun addToList(
                bitIdSymbol: String,
                bitName: String,
                bitSymbol: String,
                bitFaName: String
            ) {
                list.add(
                    DataSourceItem(
                        bitIdSymbol,
                        bitName,
                        bitSymbol,
                        bitFaName
                    )
                )
            }

            val bitIdSymbol = listOf("BTC-USDT")
            val bitNameList = listOf("Bitcoin")
            val bitSumbol = listOf("BTC")
            val bitFaName = listOf("بیتکوین")

            for (index in bitIdSymbol.indices) {
                addToList(
                    bitIdSymbol[index],
                    bitNameList[index],
                    bitSumbol[index],
                    bitFaName[index]
                )
            }
            return list
        }
    }
}