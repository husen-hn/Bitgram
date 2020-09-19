package com.husen.android.bitgram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.navigation.NavigationView

class BitGramActivity : AppCompatActivity() {

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