package com.husen.android.bitgram.api

import com.google.gson.annotations.SerializedName
import com.husen.android.bitgram.RamzinexItem

class RamzinexBitResponse {
    @SerializedName("usdtirr")
    lateinit var usdt: RamzinexFinancial
}