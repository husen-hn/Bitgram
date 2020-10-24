package com.husen.android.bitgram.response

import com.google.gson.annotations.SerializedName

class RamzinexResponse {
    @SerializedName("original")
    lateinit var bits: RamzinexBitResponse
}