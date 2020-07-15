package com.husen.android.bitgram.api

import com.google.gson.annotations.SerializedName
import com.husen.android.bitgram.GramItem

class BitResponse {
    @SerializedName("ticker")
    lateinit var gramItems: List<GramItem>
}