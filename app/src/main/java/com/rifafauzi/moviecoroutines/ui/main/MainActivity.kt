package com.rifafauzi.moviecoroutines.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupActionBarWithNavController
import com.rifafauzi.moviecoroutines.R
import com.rifafauzi.moviecoroutines.databinding.ActivityMainBinding
import dagger.android.AndroidInjection

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbar: Toolbar
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        initMain()
        setupToolbar()
        setupNavController()

    }

    private fun initMain() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.mainContent)
        toolbar = findViewById(R.id.mainToolbar)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController)
    }

    private fun setupNavController() {
        navController.addOnDestinationChangedListener(::getNavigationListener.invoke())
    }

    private fun hideToolbarSubtitle() {
        supportActionBar?.subtitle = null
    }

    private fun showToolbar(shouldShow: Boolean) {
        if (shouldShow) toolbar.visibility = View.VISIBLE else toolbar.visibility = View.GONE
    }

    private fun showToolbarBackArrow(shouldShow: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(shouldShow)
    }

    private fun getNavigationListener() =
        NavController.OnDestinationChangedListener { _, destination, _ ->
            invalidateOptionsMenu()
            hideToolbarSubtitle()
            when (destination.id) {
                R.id.movieFragment -> {
                    showToolbar(true)
                    showToolbarBackArrow(false)
                }
                else -> {
                    showToolbar(false)
                    showToolbarBackArrow(false)
                }
            }
        }

    override fun onBackPressed() {
        when (navController.currentDestination?.id) {
            R.id.movieFragment -> {
                finish()
            }
            else -> {
                navController.navigateUp()
            }
        }
    }

    override fun onSupportNavigateUp() : Boolean {
        navController.navigateUp()
        return true
    }

}
