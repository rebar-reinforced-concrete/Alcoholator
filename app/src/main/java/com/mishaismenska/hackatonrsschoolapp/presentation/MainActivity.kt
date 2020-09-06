package com.mishaismenska.hackatonrsschoolapp.presentation

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mishaismenska.hackatonrsschoolapp.R
import com.mishaismenska.hackatonrsschoolapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().replace(R.id.main_fragment_container, SplashScreenFragment()).commit()
        binding.bottomNavigationViewMain.visibility = View.INVISIBLE
        binding.bottomNavigationViewMain.setOnNavigationItemSelectedListener(this)
    }

    fun showBottomNavigation() {
        binding.bottomNavigationViewMain.visibility = View.VISIBLE
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.main_menu_item -> {
                supportFragmentManager.beginTransaction().replace(R.id.main_fragment_container, MainFragment())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit()
                true
            }
            R.id.maps_menu_item -> {
                supportFragmentManager.beginTransaction().replace(R.id.main_fragment_container, MapsFragment())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit()
                true
            }
            R.id.account_menu_item -> {
                supportFragmentManager.beginTransaction().replace(R.id.main_fragment_container, AppSettingsFragment())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit()
                true
            }
            else -> false
        }
    }
}
