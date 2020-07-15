package com.husen.android.bitgram

import com.google.gson.annotations.SerializedName
import com.husen.android.bitgram.api.BitResponse

class KucoinResponse {
    @SerializedName("data")
    lateinit var bits: BitResponse
}