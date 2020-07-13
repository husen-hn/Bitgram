package com.husen.android.bitgram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class BitgramActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bitgram)

        val isFragmentContainerEmpty = savedInstanceState == null
        if (isFragmentContainerEmpty) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragmentContainer, BitGramFragment.newInstance())
                    .commit()
        }
    }
}