package com.husen.android.bitgram

import com.google.gson.annotations.SerializedName
import com.husen.android.bitgram.api.KucoinBitResponse

class KucoinResponse {
    @SerializedName("data")
    lateinit var bits: KucoinBitResponse
}