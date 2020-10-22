package com.husen.android.bitgram.Models

import com.google.gson.annotations.SerializedName

data class RamzinexItem (
    var symbol: String = "USDT-USDT",
    @SerializedName("change_percent")var changePercent: String = "",
    @SerializedName("close")var lastPrice: String = ""
)