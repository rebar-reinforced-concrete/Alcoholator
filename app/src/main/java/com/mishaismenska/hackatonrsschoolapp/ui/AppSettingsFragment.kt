package com.mishaismenska.hackatonrsschoolapp.ui

import android.os.Bundle
import android.view.View
import androidx.preference.*
import com.mishaismenska.hackatonrsschoolapp.App
import com.mishaismenska.hackatonrsschoolapp.R
import com.mishaismenska.hackatonrsschoolapp.viewmodels.SettingsViewModel
import javax.inject.Inject


class AppSettingsFragment : PreferenceFragmentCompat() {

    @Inject
    lateinit var viewModel: SettingsViewModel

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findPreference<Preference>(getString(R.string.reset_key))?.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                viewModel.resetDB()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_container, AddUserFragment())
                    .commit()
                true
            }
        viewModel.setWeight(findPreference(getString(R.string.weight_key)))

        findPreference<SwitchPreferenceCompat>(getString(R.string.units_key))?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, _ ->
                viewModel.setWeight(findPreference(getString(R.string.weight_key)))
                true
            }

        findPreference<EditTextPreference>(getString(R.string.weight_key))?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->
                viewModel.updateWeight(newValue as String)
            }
    }
}