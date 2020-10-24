package com.husen.android.bitgram.response

import com.google.gson.annotations.SerializedName

class KucoinResponse {
    @SerializedName("data")
    lateinit var bits: KucoinBitResponse
}