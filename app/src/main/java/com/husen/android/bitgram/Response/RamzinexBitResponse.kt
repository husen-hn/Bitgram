package com.husen.android.bitgram.Network.RamzinexApi

import com.google.gson.annotations.SerializedName

class RamzinexBitResponse {
    @SerializedName("usdtirr")
    lateinit var usdt: RamzinexFinancial
}