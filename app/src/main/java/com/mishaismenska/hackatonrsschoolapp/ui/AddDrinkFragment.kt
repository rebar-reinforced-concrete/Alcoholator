package com.mishaismenska.hackatonrsschoolapp.ui

import android.icu.text.MeasureFormat
import android.icu.util.Measure
import android.icu.util.MeasureUnit
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.mishaismenska.hackatonrsschoolapp.App
import com.mishaismenska.hackatonrsschoolapp.R
import com.mishaismenska.hackatonrsschoolapp.data.TareRelations
import com.mishaismenska.hackatonrsschoolapp.data.mlToOz
import com.mishaismenska.hackatonrsschoolapp.data.models.VolumePreset
import com.mishaismenska.hackatonrsschoolapp.data.volumePreset
import com.mishaismenska.hackatonrsschoolapp.databinding.FragmentAddDrinkBinding
import com.mishaismenska.hackatonrsschoolapp.viewmodels.AddDrinkViewModel
import java.text.Format
import java.util.*
import javax.inject.Inject

class AddDrinkFragment : Fragment(), DbResultsListener {

    private lateinit var binding: FragmentAddDrinkBinding

    @Inject
    lateinit var viewModel: AddDrinkViewModel
    private lateinit var volumes: MutableList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddDrinkBinding.inflate(inflater, container, false)
        (requireActivity().application as App).appComponent.inject(this)

        volumes =
            resources.getStringArray(R.array.volume_names).toMutableList().mapIndexed { index, s ->
                s.format(
                    viewModel.formatter.format(
                        if (Locale.getDefault().country == "US") Measure(
                            mlToOz(volumePreset[VolumePreset.values()[index]]!!.number as Int),
                            MeasureUnit.OUNCE
                        )
                        else volumePreset[VolumePreset.values()[index]]
                    )
                )
            }.toMutableList()

        val drinkTypes = resources.getStringArray(R.array.drink_types)
        binding.typeInput.setAdapter(
            NoFilterAdapter(
                requireContext(),
                R.layout.drink_type_dropdown_item,
                drinkTypes
            )
        )

        binding.typeInput.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                val indexes = calculateIndexes(parent, position)
                val names = indexes.map { volumes[it] }
                indexes.map { volumes.removeAt(it) }
                binding.volumeInput.setAdapter(
                    NoFilterAdapter(
                        requireContext(),
                        R.layout.drink_type_dropdown_item,
                        (names + volumes).toTypedArray()
                    )
                )
                binding.volumeInput.setText(names.first())
                volumes = resources.getStringArray(R.array.volume_names).toMutableList()
            }

        (requireActivity().application as App).appComponent.inject(this)
        binding.goButton.setOnClickListener {
            viewModel.addDrink(binding, this)

        }
        return binding.root
    }

    private fun calculateIndexes(parent: AdapterView<*>, position: Int): List<Int> {
        val preferableVolumes =
            TareRelations[viewModel.parseDrinkType(
                binding,
                (parent.adapter.getItem(position) as String?)!!
            )]
        return preferableVolumes!!.map { it.ordinal }
    }

    override fun onDrinkAdded() {
        parentFragmentManager.popBackStack()
    }
}
