package com.husen.android.bitgram.response

import com.google.gson.annotations.SerializedName
import com.husen.android.bitgram.models.KucoinItem

class KucoinBitResponse {
    @SerializedName("ticker")
    lateinit var gramItems: List<KucoinItem>
}