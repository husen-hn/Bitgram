package com.husen.android.bitgram.response

import com.google.gson.annotations.SerializedName
import com.husen.android.bitgram.models.RamzinexItem

class RamzinexLast24h {
    @SerializedName("last24h")
    lateinit var lastData: RamzinexItem
}