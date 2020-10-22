package com.husen.android.bitgram.Response

import com.google.gson.annotations.SerializedName
import com.husen.android.bitgram.Models.KucoinItem

class KucoinBitResponse {
    @SerializedName("ticker")
    lateinit var gramItems: List<KucoinItem>
}