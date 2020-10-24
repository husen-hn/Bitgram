package com.husen.android.bitgram.response

import com.google.gson.annotations.SerializedName

class RamzinexBitResponse {
    @SerializedName("usdtirr")
    lateinit var usdt: RamzinexFinancial
}