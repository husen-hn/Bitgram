package com.husen.android.bitgram.Models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.husen.android.bitgram.Models.BitGramItem

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
    val bitUsPercent: String?
        get() = bitGramItem?.usaPercent

    @get:Bindable
    val bitUsPercentColor: Int?
        get() = bitGramItem?.usaPercentColor

    @get:Bindable
    val bitIrPrice: String?
        get() = bitGramItem?.irPrice

    @get:Bindable
    val bitIrPercent: String?
        get() = bitGramItem?.irPercent

    @get:Bindable
    val bitIrPercentColor: Int?
        get() = bitGramItem?.irPercentColor

    companion object {
        fun newInstance(): BitGramViewModelVM {
            return BitGramViewModelVM()
        }
    }
}