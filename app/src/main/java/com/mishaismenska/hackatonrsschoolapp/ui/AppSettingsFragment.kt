package com.mishaismenska.hackatonrsschoolapp.ui

import android.content.Context
import android.content.SharedPreferences
import android.icu.text.MeasureFormat
import android.icu.util.Measure
import android.icu.util.MeasureUnit
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentTransaction
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.mishaismenska.hackatonrsschoolapp.App
import com.mishaismenska.hackatonrsschoolapp.R
import com.mishaismenska.hackatonrsschoolapp.viewmodels.SettingsViewModel
import java.util.*
import javax.inject.Inject


class AppSettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    @Inject
    lateinit var viewModel: SettingsViewModel

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findPreference<Preference>(getString(R.string.reset_key))?.onPreferenceClickListener =
            resetClickListener
        viewModel.setWeightPreference(Locale.getDefault().country == "US")
        findPreference<EditTextPreference>(getString(R.string.weight_key))!!.summaryProvider =
            weightSummaryProvider
        findPreference<EditTextPreference>(getString(R.string.weight_key))!!.onPreferenceClickListener =
            weightClickListener
        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    private val resetClickListener =
        Preference.OnPreferenceClickListener {
            viewModel.resetDB()
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, AddUserFragment()).setTransition(
                    FragmentTransaction.TRANSIT_FRAGMENT_OPEN
                ).commit()
            true
        }

    private val weightClickListener =
        Preference.OnPreferenceClickListener {
            findPreference<EditTextPreference>(getString(R.string.weight_key))!!.text =
                findPreference<EditTextPreference>(getString(R.string.weight_key))!!.summary.toString()
            true
        }

    private val weightSummaryProvider =
        Preference.SummaryProvider<EditTextPreference> {
            val format = MeasureFormat.getInstance(
                Locale.getDefault(),
                MeasureFormat.FormatWidth.WIDE
            )
            val weight = getCleanedWeight()
            if (Locale.getDefault().country == "US")
                format.format(Measure(weight, MeasureUnit.POUND))
            else
                format.format(Measure(weight, MeasureUnit.KILOGRAM))
        }

    private fun getCleanedWeight() =
        preferenceManager.sharedPreferences.getString(
            "weight", ""
        )!!.split('.')[0].filter { it.isDigit() }.toInt()

    override fun onDestroyView() {
        super.onDestroyView()
        preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(pref: SharedPreferences?, key: String?) {
        if (key == requireContext().getString(R.string.weight_key)) {
            viewModel.updateWeight(
                pref!!.getString(key, "yo mommas weri gay"), Locale.getDefault().country == "US"
            )
        }
    }
}
