package com.husen.android.bitgram

import com.google.gson.annotations.SerializedName

data class GramItem(
    var symbol: String = "",
    var changePrice: String = "",
    @SerializedName("last") var lastPrice: String = ""
)