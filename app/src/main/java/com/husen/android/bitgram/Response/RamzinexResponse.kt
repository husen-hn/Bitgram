package com.husen.android.bitgram.Response

import com.google.gson.annotations.SerializedName
import com.husen.android.bitgram.Response.RamzinexBitResponse

class RamzinexResponse {
    @SerializedName("original")
    lateinit var bits: RamzinexBitResponse
}