package com.husen.android.bitgram.api

import com.google.gson.annotations.SerializedName
import com.husen.android.bitgram.KucoinItem

class KucoinBitResponse {
    @SerializedName("ticker")
    lateinit var gramItems: List<KucoinItem>
}