package com.mishaismenska.hackatonrsschoolapp.presentation

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.preference.EditTextPreference
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.android.material.snackbar.Snackbar
import com.mishaismenska.hackatonrsschoolapp.R
import com.mishaismenska.hackatonrsschoolapp.di.App
import com.mishaismenska.hackatonrsschoolapp.presentation.viewmodels.SettingsViewModel
import com.mishaismenska.hackatonrsschoolapp.staticPresets.AppConstants.defaultGenderId
import java.util.*
import javax.inject.Inject


class AppSettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {
    @Inject
    lateinit var viewModel: SettingsViewModel

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        (requireActivity().application as App).appComponent.inject(this)
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val resetPreference = findPreference<Preference>(getString(R.string.reset_key))
        val namePreference = findPreference<EditTextPreference>(getString(R.string.name_key))
        val weightPreference = findPreference<EditTextPreference>(getString(R.string.weight_key))
        val genderPreference = findPreference<ListPreference>(getString(R.string.gender_key))

        viewModel.userLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            viewModel.loadName(namePreference!!)
            viewModel.loadWeight(Locale.getDefault().country == "US", weightPreference!!)
            viewModel.loadGender(genderPreference!!)
        })

        resetPreference!!.setOnPreferenceClickListener {
            viewModel.resetDB()
            parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, AddUserFragment()).setTransition(
                FragmentTransaction.TRANSIT_FRAGMENT_OPEN
            ).commit()
            Snackbar.make(view, "All your data has been removed", Snackbar.LENGTH_LONG).show()
            true
        }
        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(pref: SharedPreferences?, key: String?) {
        when (key) {
            requireContext().getString(R.string.weight_key) -> {
                viewModel.updateWeight(
                    pref!!.getString(key, "0"), Locale.getDefault().country == "US"
                )
            }
            requireContext().getString(R.string.name_key) -> {
                viewModel.updateName(
                    pref!!.getString(key, "")
                )
            }
            requireContext().getString(R.string.gender_key) -> {
                viewModel.updateGender(
                    pref!!.getString(key, defaultGenderId.toString())
                )
            }
        }
    }

}
