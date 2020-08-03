package com.mishaismenska.hackatonrsschoolapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.mishaismenska.hackatonrsschoolapp.App
import com.mishaismenska.hackatonrsschoolapp.R
import com.mishaismenska.hackatonrsschoolapp.data.TareRelations
import com.mishaismenska.hackatonrsschoolapp.databinding.FragmentAddDrinkBinding
import com.mishaismenska.hackatonrsschoolapp.viewmodels.AddDrinkViewModel
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
        volumes = resources.getStringArray(R.array.volume_names).toMutableList()
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
                val indexes = calculateIndices(parent, position)
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

    private fun calculateIndices(parent: AdapterView<*>, position: Int): List<Int> {
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
