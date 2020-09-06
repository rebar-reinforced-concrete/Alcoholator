package com.mishaismenska.hackatonrsschoolapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
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

    private lateinit var binding: AppSettingsFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as App).appComponent.inject(this)
        viewModel.getUser()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AppSettingsFragmentBinding.inflate(inflater, container, false)
        viewModel.userLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer { user ->
            binding.currentUserName.text = user.userName
            binding.genderPreferenceDescription.text = viewModel.getGenders()[user.genderId]
            binding.currentUserWeight.text = viewModel.getFormattedWeight(user.weight)

            binding.namePreference.setOnClickListener {
                alertDialogManager.showEditNameAlertDialog(
                    user.userName,
                    requireContext(),
                    viewModel::updateName
                )
            }

            binding.weightPreference.setOnClickListener {
                alertDialogManager.showEditWeightAlertDialog(
                    user.weight.toString(),
                    requireContext(),
                    viewModel::updateWeight
                )
            }
            binding.genderPreference.setOnClickListener {
                alertDialogManager.showEditGenderAlertDialog(user.genderId, requireContext(), viewModel::updateGender)
            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.reset.setOnClickListener {
            viewModel.resetDB()
            openAddUserFragment()
        }
        viewModel.showWrongWeightSnackbar.observe(viewLifecycleOwner, Observer {
            if (it)
                Snackbar.make(binding.root, R.string.too_weird_error, Snackbar.LENGTH_LONG).show()
        })
    }

    private fun openAddUserFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, AddUserFragment().apply {
                arguments = bundleOf(AppConstants.DATA_REMOVED_KEY to true)
            }).setTransition(
                FragmentTransaction.TRANSIT_FRAGMENT_OPEN
            ).commit()
    }
}
