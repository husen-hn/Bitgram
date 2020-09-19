package com.husen.android.bitgram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.navigation.NavigationView
import com.husen.android.bitgram.NavigationDrawerItems.SuggestionFragment
import kotlinx.android.synthetic.main.navigation_drawer.*

class BitGramActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val bitGramViewModel: BitGramViewModel by lazy {
        ViewModelProvider(this).get(BitGramViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigation_drawer)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navigationView: NavigationView = findViewById(R.id.navigation_view)

        setSupportActionBar(toolbar)
        navigationView.setNavigationItemSelectedListener(this)

        //Navigation Drawer
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open_drawer,
            R.string.close_drawer)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // RTL navigation drawer
        toolbar.setNavigationOnClickListener {
            if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                drawerLayout.closeDrawer(Gravity.RIGHT)
            } else {
                drawerLayout.openDrawer(Gravity.RIGHT)
            }
        }

        //Display first fragment
        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer)

        if (currentFragment == null) {
            val fragment = BitGramFragment.newInstance()
            replaceFragment(fragment)
        }
//        val isFragmentContainerEmpty = savedInstanceState == null
//        if (isFragmentContainerEmpty) {
//            supportFragmentManager
//                    .beginTransaction()
//                    .add(R.id.fragmentContainer, BitGramFragment.newInstance())
//                    .commit()
//        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.familiarity_id -> { Log.d("XXXX", "familiarity_id")}
            R.id.suggestion_id -> {
                val fragment = SuggestionFragment.newInstance()
                replaceFragment(fragment)
            }
            R.id.privacy_id -> {Log.d("XXXX", "privacy_id")}
            R.id.about_id -> {Log.d("XXXX", "about_id")}
            R.id.share_id -> {Log.d("XXXX", "share_id")}
            R.id.changes_id -> {Log.d("XXXX", "changes_id")}
        }
        drawer_layout.closeDrawer(GravityCompat.END)

        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.search_id -> object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    TODO("Not yet implemented")
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    TODO("Not yet implemented")
                }

                override fun afterTextChanged(p0: Editable?) {
                    filterList(p0.toString())
                }
            }
        }
        return false
    }

    private fun filterList(filterItem: String) {

//        val dataSourceList = bitGramViewModel.dataSourceList

//        val kucoinItemList = bitGramViewModel.gramItemLiveData.observe(
//            viewLifecycleOwner,
//            Observer { gramItem ->
//
//            })
//
//        for (d in dataSourceList){
//            if (filterItem in d.bitIdSymbol) {
//
//            }
//        }
//        BitGramFragment.newInstance().updateList()
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
}