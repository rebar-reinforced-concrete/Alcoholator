package com.mishaismenska.hackatonrsschoolapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.mishaismenska.hackatonrsschoolapp.R

const val USER_CREATED = "userCreated"

class SplashScreenFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(requireActivity().application)
        if (sharedPreferences.getBoolean(USER_CREATED, false))
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, MainFragment()).commit()
        else parentFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, AddUserFragment()).commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

}
