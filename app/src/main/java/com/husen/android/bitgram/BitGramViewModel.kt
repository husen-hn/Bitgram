package com.husen.android.bitgram

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class BitGramViewModel : ViewModel() {

    var gramItemLiveData: LiveData<List<GramItem>>
    var dataSourceList: HashMap<String, DataSourceItem>

    private fun gramItemLiveData(): LiveData<List<GramItem>> {
        return KucoinFetchr().fetchBits()
    }

    private fun dataSourceList(): HashMap<String, DataSourceItem> {
        return KucoinFetchr().getDataSourceItem()
    }

    init {
        gramItemLiveData = gramItemLiveData()
        dataSourceList = dataSourceList()
    }
}