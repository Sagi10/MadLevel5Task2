package com.lalee.madlevel5task2.ui

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.lalee.madlevel5task2.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_fab.*

class MainActivity : AppCompatActivity() {

    private var navController: Int = R.id.nav_host_fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        fab.setOnClickListener {
            findNavController(navController).navigate(R.id.action_FirstFragment_to_SecondFragment)
            fabToggler()
        }
    }

    private fun fabToggler() {
        findNavController(navController).addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.SecondFragment) {
                fab.hide()
            } else if (destination.id == R.id.FirstFragment) {
                fab.show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}