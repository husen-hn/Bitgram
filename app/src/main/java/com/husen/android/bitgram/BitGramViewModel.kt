package com.husen.android.bitgram

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class BitGramViewModel : ViewModel() {
    val gramItemLiveData: LiveData<List<GramItem>>
    var dataSourceList = ArrayList<DataSourceItem?>()

    init {
        gramItemLiveData = KucoinFetchr().fetchBits()
    }

    fun listDataSource() {
        dataSourceList = KucoinFetchr().getDataSourceItem()
    }
}