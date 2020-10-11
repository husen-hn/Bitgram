package com.husen.android.bitgram

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import coil.api.load

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

//    @get:Bindable
//    val bitUsPercentColor: Int?
//        get() = bitGramItem?.usaPercentColor

    @get:Bindable
    val bitIrPrice: String?
        get() = bitGramItem?.irPrice

    @get:Bindable
    val bitIrPercent: String?
        get() = bitGramItem?.irPercent

//    @get:Bindable
//    val bitIrPercentColor: Int?
//        get() = bitGramItem?.irPercentColor

    private fun getLogoUrl(): String? {
        return bitGramItem?.bitLogoURL
    }

    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, url: String) {
        view.load(url)
    }

/*    @BindingAdapter("myColorAttr")
    fun setPercentColor(view: TextView, percent: String): Int? {
        val percentInDouble = percent.toDouble()
        var color: Int? = null
        val greenColor = ContextCompat.getColor(view.context, R.color.green)
        val darkerRedColor = ContextCompat.getColor(view.context, R.color.darkerRed)
        when{
            percentInDouble >= 0.0 -> {
                color = greenColor
            }
            percentInDouble < 0.0 -> {
                color = darkerRedColor
            }
        }
        return color
    } */
}