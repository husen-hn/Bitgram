package com.husen.android.bitgram.Response

import com.google.gson.annotations.SerializedName
import com.husen.android.bitgram.Response.RamzinexLast24h

class RamzinexFinancial {
    @SerializedName("financial")
    lateinit var financialData: RamzinexLast24h
}