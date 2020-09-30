package com.husen.android.bitgram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.navigation.NavigationView

private const val TAG = "BitGramActivity"

class BitGramActivity : AppCompatActivity() {

    private val bitGramViewModel: BitGramViewModel by lazy {
        ViewModelProvider(this).get(BitGramViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bitgram)

        //Display first fragment
        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer)

        if (currentFragment == null) {
            val fragment = BitGramFragment.newInstance()
            replaceFragment(fragment)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
}