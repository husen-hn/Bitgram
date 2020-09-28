package com.husen.android.bitgram.api

import com.google.gson.annotations.SerializedName
import com.husen.android.bitgram.RamzinexItem

class RamzinexLast24h {
    @SerializedName("last24h")
    lateinit var lastData: RamzinexItem
}