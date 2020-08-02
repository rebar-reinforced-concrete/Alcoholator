package com.mishaismenska.hackatonrsschoolapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mishaismenska.hackatonrsschoolapp.App
import com.mishaismenska.hackatonrsschoolapp.R
import com.mishaismenska.hackatonrsschoolapp.databinding.FragmentAddDrinkBinding
import com.mishaismenska.hackatonrsschoolapp.databinding.FragmentAddUserBinding
import com.mishaismenska.hackatonrsschoolapp.viewmodels.AddUserViewModel
import javax.inject.Inject

class AddUserFragment : Fragment(), DbResultsListener {

    private lateinit var binding: FragmentAddUserBinding
    @Inject
    lateinit var viewModel: AddUserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddUserBinding.inflate(inflater, container, false)
        val genderNames = resources.getStringArray(R.array.genders_names)
        binding.genderInput.setAdapter(NoFilterAdapter(requireContext(), R.layout.gender_dropdown_item, genderNames))
        binding.genderInput.setText(genderNames.last())
        (requireActivity().application as App).appComponent.inject(this)
        binding.goButton.setOnClickListener {
            if(viewModel.validate(binding)){
                viewModel.addUser(binding, this)
            }
        }
        return binding.root
    }

    override fun onUserAdded() {
        parentFragmentManager.beginTransaction().replace(R.id.main_fragment_container, MainFragment()).commit()
    }
}
