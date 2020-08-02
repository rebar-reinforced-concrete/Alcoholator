package com.mishaismenska.hackatonrsschoolapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mishaismenska.hackatonrsschoolapp.App
import com.mishaismenska.hackatonrsschoolapp.R
import com.mishaismenska.hackatonrsschoolapp.databinding.FragmentAddDrinkBinding
import com.mishaismenska.hackatonrsschoolapp.viewmodels.AddDrinkViewModel
import javax.inject.Inject

class AddDrinkFragment : Fragment() {

    private lateinit var binding: FragmentAddDrinkBinding

    @Inject
    lateinit var viewModel: AddDrinkViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddDrinkBinding.inflate(inflater, container, false)

        val drinkTypes = resources.getStringArray(R.array.drink_types)
        binding.typeInput.setAdapter(
            NoFilterAdapter(
                requireContext(),
                R.layout.drink_type_dropdown_item,
                drinkTypes
            )
        )
        binding.volumeInput.setText(drinkTypes.first())
        val volumes = resources.getStringArray(R.array.volume_names)
        binding.volumeInput.setAdapter(
            NoFilterAdapter(
                requireContext(),
                R.layout.drink_type_dropdown_item,
                volumes
            )
        )
        binding.volumeInput.setText(volumes.first())
        (requireActivity().application as App).appComponent.inject(this)
        binding.goButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, MainFragment()).commit()
        }
        return binding.root
    }

}
