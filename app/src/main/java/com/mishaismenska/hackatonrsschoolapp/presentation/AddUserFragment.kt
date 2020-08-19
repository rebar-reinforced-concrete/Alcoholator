package com.mishaismenska.hackatonrsschoolapp.presentation

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.mishaismenska.hackatonrsschoolapp.R
import com.mishaismenska.hackatonrsschoolapp.databinding.FragmentAddUserBinding
import com.mishaismenska.hackatonrsschoolapp.di.App
import com.mishaismenska.hackatonrsschoolapp.presentation.interfaces.UserInputValidatingManager
import com.mishaismenska.hackatonrsschoolapp.presentation.viewmodels.AddUserViewModel
import com.mishaismenska.hackatonrsschoolapp.staticPresets.AppConstants
import javax.inject.Inject

class AddUserFragment : Fragment() {

    private lateinit var binding: FragmentAddUserBinding

    @Inject
    lateinit var viewModel: AddUserViewModel

    @Inject
    lateinit var userInputValidatingManager: UserInputValidatingManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddUserBinding.inflate(inflater, container, false)
        val genderNames = resources.getStringArray(R.array.genders_names)
        binding.genderInput.setAdapter(NoFilterAdapter(requireContext(), R.layout.gender_dropdown_item, genderNames))
        binding.genderInput.setText(genderNames.last())
        binding.genderInput.keyListener = null
        (requireActivity().application as App).appComponent.inject(this)
        binding.weightInputWrapper.hint = viewModel.getWeightInputHint()
        binding.goButton.setOnClickListener {
            if (userInputValidatingManager.validateUserInput(binding)) {
                addUser()
            }
        }
        viewModel.isFragmentOpened.observe(viewLifecycleOwner, Observer {
            if (!it) {
                openMainFragment()
            }
        })
        return binding.root
    }

    private fun addUser() {
        val age = binding.ageInput.text.toString().toInt()
        val weight = binding.weightInput.text.toString().toDouble()
        val gender = binding.genderInput.text.toString()
        viewModel.addUser(age, weight, gender)
    }

    override fun onResume() {
        super.onResume()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments?.getBoolean(AppConstants.DATA_REMOVED_KEY, false) == true) {
            Snackbar.make(view, getString(R.string.data_removed), Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onPause() {
        super.onPause()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
    }

    private fun openMainFragment() {
        parentFragmentManager
            .beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
            .replace(R.id.main_fragment_container, MainFragment())
            .commit()
    }
}
