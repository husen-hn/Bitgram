package com.husen.android.bitgram.Response

import com.google.gson.annotations.SerializedName
import com.husen.android.bitgram.Response.RamzinexFinancial

class RamzinexBitResponse {
    @SerializedName("usdtirr")
    lateinit var usdt: RamzinexFinancial
}