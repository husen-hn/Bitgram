package com.husen.android.bitgram

data class BitGramItem(
    var bitLogoURL: String = "",
    var bitName: String = "",
    var bitSymbol: String = "",
    var bitFaName: String = "",
    var usaPrice: String = "",
    var usaPercent: String = "",
    var usaPercentColor: Int?,
    var irPrice: String = "",
    var irPercent: String = "",
    var irPercentColor: Int?
)