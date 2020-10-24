package com.husen.android.bitgram.response

import com.google.gson.annotations.SerializedName

class RamzinexFinancial {
    @SerializedName("financial")
    lateinit var financialData: RamzinexLast24h
}