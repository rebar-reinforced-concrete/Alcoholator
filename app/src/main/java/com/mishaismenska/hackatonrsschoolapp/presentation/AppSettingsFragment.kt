package com.mishaismenska.hackatonrsschoolapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.snackbar.Snackbar
import com.mishaismenska.hackatonrsschoolapp.R
import com.mishaismenska.hackatonrsschoolapp.databinding.AppSettingsFragmentBinding
import com.mishaismenska.hackatonrsschoolapp.di.App
import com.mishaismenska.hackatonrsschoolapp.presentation.viewmodels.SettingsViewModel
import com.mishaismenska.hackatonrsschoolapp.staticPresets.AppConstants
import javax.inject.Inject


class AppSettingsFragment : Fragment() {
    @Inject
    lateinit var viewModel: SettingsViewModel
    @Inject
    lateinit var alertDialogManager: AlertDialogManagerImpl

    private var _binding: AppSettingsFragmentBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AppSettingsFragmentBinding.inflate(inflater, container, false)
        (requireActivity().application as App).appComponent.inject(this)
        viewModel.userLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            viewModel.loadName(binding.currentUserName)
            viewModel.loadWeight(binding.currentUserWeight)
            viewModel.loadGender(binding.genderSpinner)
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.genderSpinner.adapter = NoFilterAdapter(
            requireContext(),
            R.layout.gender_dropdown_item,
            resources.getStringArray(R.array.genders_names)
        )
        binding.namePreference.setOnClickListener {
            alertDialogManager.showEditNameAlertDialog(
                viewModel.getUserName(),
                requireContext(),
                ::updateName
            )
        }
        binding.weightPreference.setOnClickListener {
            alertDialogManager.showEditWeightAlertDialog(
                viewModel.getUserWeight(),
                requireContext(),
                ::updateWeight
            )
        }
        binding.genderSpinner.onItemSelectedListener = viewModel.genderChangedListener
        binding.reset.setOnClickListener {
            viewModel.resetDB()
            openAddUserFragment()
        }
    }

    private fun openAddUserFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, AddUserFragment().apply {
                arguments = bundleOf(AppConstants.DATA_REMOVED_KEY to true)
            }).setTransition(
                FragmentTransaction.TRANSIT_FRAGMENT_OPEN
            ).commit()
    }

    private fun updateName(newValue: String) {
        viewModel.updateName(newValue)
        binding.currentUserName.text = newValue
    }

    private fun updateWeight(newValue: String) {
        binding.currentUserWeight.text = viewModel.updateWeight(newValue)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
