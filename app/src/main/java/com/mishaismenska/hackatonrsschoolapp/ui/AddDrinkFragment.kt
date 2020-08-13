package com.mishaismenska.hackatonrsschoolapp.ui

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
import com.mishaismenska.hackatonrsschoolapp.data.mlToOz
import com.mishaismenska.hackatonrsschoolapp.data.staticPresets.VolumePresets
import com.mishaismenska.hackatonrsschoolapp.databinding.FragmentAddDrinkBinding
import com.mishaismenska.hackatonrsschoolapp.viewmodels.AddDrinkViewModel
import java.util.*
import javax.inject.Inject

class AddDrinkFragment : Fragment(), DbResultsListener, AdapterView.OnItemClickListener {

    private lateinit var binding: FragmentAddDrinkBinding

    @Inject
    lateinit var viewModel: AddDrinkViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddDrinkBinding.inflate(inflater, container, false)
        (requireActivity().application as App).appComponent.inject(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val drinkTypes = resources.getStringArray(R.array.drink_types)
        binding.typeInput.setAdapter(
            NoFilterAdapter(requireContext(), R.layout.drink_type_dropdown_item, drinkTypes)
        )
        binding.volumeInput.setAdapter(
            NoFilterAdapter(requireContext(), R.layout.drink_type_dropdown_item, getVolumeStrings())
        )
        binding.typeInput.onItemClickListener = this
        binding.typeInput.setText(drinkTypes.first())
        binding.volumeInput.setText(binding.volumeInput.adapter.getItem(0).toString())
        binding.goButton.setOnClickListener { viewModel.addDrink(binding, this) }
    }

    private fun getVolumeStrings(): Array<String> =
        resources.getStringArray(R.array.volume_names).toMutableList().mapIndexed { index, s ->
            s.format(
                viewModel.formatter.format(
                    if (Locale.getDefault().country == "US") Measure(
                        mlToOz(VolumePresets.values()[index].volume.number as Int),
                        MeasureUnit.OUNCE
                    )
                    else VolumePresets.values()[index]
                )
            )
        }.toTypedArray()

    private fun calculateIndexes(parent: AdapterView<*>, position: Int): List<Int> {
        val preferableVolumes = viewModel.parseDrinkType(
            binding,
            (parent.adapter.getItem(position) as String?)!!
        ).typicalTares
        return preferableVolumes.map { it.ordinal }
    }

    override fun onDrinkAdded() {
        parentFragmentManager.popBackStack()
    }

    override fun onItemClick(parent: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        val indexes = calculateIndexes(parent!!, position)
        val volumes: MutableList<String> = getVolumeStrings().toMutableList()
        val names = indexes.map { volumes[it] }
        indexes.map { volumes.removeAt(it) }
        binding.volumeInput.setAdapter(
            NoFilterAdapter(requireContext(), R.layout.drink_type_dropdown_item,
                (names + volumes).toTypedArray()
            )
        )
        binding.volumeInput.setText(names.first())
    }
}