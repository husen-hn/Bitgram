package com.husen.android.bitgram

import android.content.Context

class BitGramRepository private constructor(context: Context) {

    fun getDataSourceItem(): ArrayList<DataSourceItem?> {
        return DataSource.getList()
    }

    companion object {
        private var INSTANCE: BitGramRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = BitGramRepository(context)
            }
        }
        fun get(): BitGramRepository {
            return INSTANCE
                ?: throw IllegalStateException("BitGramRepository must be initialized")
        }
    }
}