package com.husen.android.bitgram

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

class BitGramViewModelVM: BaseObservable() {

    var bitGramItem: BitGramItem? = null
        set(value) {
            field = value
            notifyChange()
        }

    @get:Bindable
    val imgUrl: String?
        get() = bitGramItem?.bitLogoURL

    @get:Bindable
    val bitName: String?
        get() = bitGramItem?.bitName

    @get:Bindable
    val bitSymbol: String?
        get() = bitGramItem?.bitSymbol

    @get:Bindable
    val bitFaName: String?
        get() = bitGramItem?.bitFaName

    @get:Bindable
    val bitUsPrice: String?
        get() = bitGramItem?.usaPrice

    @get:Bindable
    val bitUsPercentIcon: String?
        get() = bitGramItem?.usaPercent//TODO

    @get:Bindable
    val bitUsPercent: String?
        get() = bitGramItem?.usaPercent

    @get:Bindable
    val bitUsPercentColor: String?
        get() = bitGramItem?.usaPercent//TODO

    @get:Bindable
    val bitIrPrice: String?
        get() = bitGramItem?.irPrice

    @get:Bindable
    val bitIrPercentIcon: String?
        get() = bitGramItem?.bitName//TODO

    @get:Bindable
    val bitIrPercent: String?
        get() = bitGramItem?.irPercent

    @get:Bindable
    val bitIrPercentColor: String?
        get() = bitGramItem?.irPercent//TODO
}