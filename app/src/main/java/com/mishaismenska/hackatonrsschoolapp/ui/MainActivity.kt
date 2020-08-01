package com.mishaismenska.hackatonrsschoolapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mishaismenska.hackatonrsschoolapp.R

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.q, MainFragment()).commit()
    }
}
