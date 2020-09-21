package com.husen.android.bitgram

import androidx.lifecycle.*

class BitGramViewModel : ViewModel() {

    var gramItemLiveData: LiveData<List<BitGramItem>>

    private fun gramItemLiveData(): LiveData<List<BitGramItem>> {
        return ApiFetchr().fetchKucoinBits()
    }

    init {
        gramItemLiveData = gramItemLiveData()
    }
}