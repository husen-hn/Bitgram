package com.husen.android.bitgram.Response

import com.google.gson.annotations.SerializedName
import com.husen.android.bitgram.Response.KucoinBitResponse

class KucoinResponse {
    @SerializedName("data")
    lateinit var bits: KucoinBitResponse
}