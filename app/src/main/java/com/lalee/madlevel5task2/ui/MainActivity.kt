package com.lalee.madlevel5task2.ui

import android.app.AlertDialog
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.lalee.madlevel5task2.R
import com.lalee.madlevel5task2.viewmodel.GameViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_fab.*

class MainActivity : AppCompatActivity() {

    private var navController: Int = R.id.nav_host_fragment
    private var deleteMenuItem: MenuItem? = null

    private val gameViewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        fab.setOnClickListener {
            findNavController(navController).navigate(R.id.action_FirstFragment_to_SecondFragment)
            findNavController(navController).addOnDestinationChangedListener { controller, destination, arguments ->
                if (destination.id == R.id.SecondFragment) {
                    deleteMenuItem?.isVisible = false
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                    supportActionBar?.setDisplayShowHomeEnabled(true)
                } else {
                    deleteMenuItem?.isVisible = true
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                    supportActionBar?.setDisplayShowHomeEnabled(false)
                }
            }
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
        deleteMenuItem = menu.findItem(R.id.action_settings_delete)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings_delete -> {
                //findNavController(navController).addOnDestinationChangedListener { controller, destination, arguments ->
                   // if (destination.id == R.id.FirstFragment) {
                        item.setOnMenuItemClickListener {
                            val builder = AlertDialog.Builder(this)
                            builder.setMessage("Are you sure you want to DELETE?")
                                .setCancelable(false)
                                .setPositiveButton("YES") { _, _ ->
                                    // Delete history
                                    gameViewModel.deleteAllGames()

                                    Toast.makeText(
                                        applicationContext,
                                        "HISTORY DELETED",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                .setNegativeButton("NO") { dialog, _ ->
                                    // Dismiss the dialog
                                    dialog.dismiss()
                                }
                            val alert = builder.create()
                            alert.show()
                            true

                      //  }
                  //  }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}