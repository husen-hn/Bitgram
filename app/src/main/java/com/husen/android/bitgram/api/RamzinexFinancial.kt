package com.husen.android.bitgram.api

import com.google.gson.annotations.SerializedName

class RamzinexFinancial {
    @SerializedName("financial")
    lateinit var financialData: RamzinexLast24h
}