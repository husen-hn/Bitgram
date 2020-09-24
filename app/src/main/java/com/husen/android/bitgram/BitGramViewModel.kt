package com.husen.android.bitgram

import androidx.lifecycle.*

class BitGramViewModel : ViewModel() {

    var bitGramItemsLiveData: LiveData<List<BitGramItem>>

    private fun bitGramItemsLiveData(): LiveData<List<BitGramItem>> {
        return ApiFetchr().fetchCollectedData()
    }

    init {
        bitGramItemsLiveData = bitGramItemsLiveData()
    }
}