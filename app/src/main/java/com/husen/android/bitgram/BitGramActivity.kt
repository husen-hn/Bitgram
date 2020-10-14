package com.husen.android.bitgram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.husen.android.bitgram.databinding.ActivityBitgramBinding

private const val TAG = "BitGramActivity"

class BitGramActivity : AppCompatActivity() {

    lateinit var binding: ActivityBitgramBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bitgram)
    }
}