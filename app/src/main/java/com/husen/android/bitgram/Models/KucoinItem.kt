package com.husen.android.bitgram.Models

import com.google.gson.annotations.SerializedName

data class KucoinItem(
    var symbol: String = "",
    var changePrice: String = "",
    @SerializedName("last") var lastPrice: String = ""
)