package com.seejiekai.quizappcs

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.seejiekai.quizappcs.core.service.AuthService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var authService: AuthService
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.profileFragment), drawerLayout
        )

        val navView = findViewById<NavigationView>(R.id.navigationView)
        navView.setupWithNavController(navController)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        setupActionBarWithNavController(navController, appBarConfiguration)

        if (authService.getUid() != null) {
            Navigation()
        }

        navController.addOnDestinationChangedListener { _, dest, _ ->
            if (dest.id == R.id.loginFragment || dest.id == R.id.registerFragment) {
                toolbar.visibility = View.GONE
            } else {
                toolbar.visibility = View.VISIBLE
            }
        }

        navView.menu.findItem(R.id.logout).setOnMenuItemClickListener {
            authService.logout()
            NavigationNavView()
            drawerLayout.close()
            true
        }
    }

    override fun onNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onNavigateUp()
    }

    private fun Navigation() {
        navController.navigate(
            R.id.homeFragment,
            null,
            NavOptions.Builder()
                .setPopUpTo(R.id.loginFragment, true)
                .build()
        )
    }

    private fun NavigationNavView() {
        navController.navigate(
            R.id.loginFragment,
            null,
            NavOptions.Builder()
                .setPopUpTo(R.id.homeFragment, true)
                .build()
        )
    }
}