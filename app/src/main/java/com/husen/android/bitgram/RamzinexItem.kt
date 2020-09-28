package com.husen.android.bitgram

import com.google.gson.annotations.SerializedName

data class RamzinexItem (
    var symbol: String = "USDT-USDT",
    @SerializedName("change_percent")var changePercent: String = "",
    @SerializedName("highest")var lastPrice: String = ""
)