package com.husen.android.bitgram.Response

import com.google.gson.annotations.SerializedName
import com.husen.android.bitgram.Models.RamzinexItem

class RamzinexLast24h {
    @SerializedName("last24h")
    lateinit var lastData: RamzinexItem
}