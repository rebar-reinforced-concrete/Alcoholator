package com.mishaismenska.hackatonrsschoolapp.ui

import android.content.SharedPreferences
import android.icu.text.MeasureFormat
import android.icu.util.LocaleData
import android.icu.util.Measure
import android.icu.util.MeasureUnit
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.preference.*
import com.mishaismenska.hackatonrsschoolapp.App
import com.mishaismenska.hackatonrsschoolapp.R
import com.mishaismenska.hackatonrsschoolapp.data.kgToLb
import com.mishaismenska.hackatonrsschoolapp.data.lbToKg
import com.mishaismenska.hackatonrsschoolapp.viewmodels.SettingsViewModel
import java.lang.Character.isDigit
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
            Preference.OnPreferenceClickListener {
                viewModel.resetDB()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_container, AddUserFragment())
                    .commit()
                true
            }


        val format = MeasureFormat.getInstance(Locale.getDefault(), MeasureFormat.FormatWidth.WIDE)
        viewModel.setWeightPreference(Locale.getDefault().country == "US")

        findPreference<EditTextPreference>(getString(R.string.weight_key))!!.summaryProvider =
            Preference.SummaryProvider<EditTextPreference> {
                if (Locale.getDefault().country == "US") {
                    format.format(
                        Measure(preferenceManager.sharedPreferences.getString("weight", "0 pounds")!!.filter{c -> c.isDigit()}.toInt(), MeasureUnit.POUND)
                    )
                } else {
                    format.format(
                        Measure(preferenceManager.sharedPreferences.getString("weight", "0 pounds")!!.filter{ c -> c.isDigit()}.toInt(), MeasureUnit.KILOGRAM)
                    )
                }
            }

        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)

    }

    override fun onSharedPreferenceChanged(pref: SharedPreferences?, key: String?) {
        if (key == getString(R.string.weight_key)) {
            viewModel.updateWeight(
                pref!!.getString(key, "yo mommas weri gay"),Locale.getDefault().country == "US"
            )
        }
    }
}