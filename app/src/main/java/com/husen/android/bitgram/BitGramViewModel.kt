package com.husen.android.bitgram

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class BitGramViewModel : ViewModel() {

    var gramItemLiveData: LiveData<List<BitGramItem>>

    private fun gramItemLiveData(): LiveData<List<BitGramItem>> {
        return KucoinFetchr().fetchBits()
    }

    init {
        gramItemLiveData = gramItemLiveData()
    }
}