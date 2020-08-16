package com.mishaismenska.hackatonrsschoolapp.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.mishaismenska.hackatonrsschoolapp.R
import com.mishaismenska.hackatonrsschoolapp.di.App
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.GetUserExistenceUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

const val USER_CREATED = "userCreated"

class SplashScreenFragment : Fragment() {

    @Inject
    lateinit var existenceUseCase: GetUserExistenceUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as App).appComponent.inject(this)
        lifecycleScope.launch(Dispatchers.IO){
            if (existenceUseCase.checkIfUserExists()){
                Log.d("Splash", "user exists")
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_container, MainFragment()).commit()
            }
            else {
                Log.d("Splash", "user doesn't exist")
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_container, AddUserFragment()).commit()
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

}
