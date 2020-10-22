package com.husen.android.bitgram

import com.google.gson.annotations.SerializedName

data class KucoinItem(
    var symbol: String = "",
    var changePrice: String = "",
    @SerializedName("last") var lastPrice: String = ""
)