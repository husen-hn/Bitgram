package com.husen.android.bitgram

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class BitGramViewModel : ViewModel() {
    val gramItemLiveData: LiveData<List<GramItem>>

    init {
        gramItemLiveData = KucoinFetchr().fetchBits()
    }
}