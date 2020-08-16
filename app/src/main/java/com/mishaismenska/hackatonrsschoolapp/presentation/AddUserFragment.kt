package com.mishaismenska.hackatonrsschoolapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.mishaismenska.hackatonrsschoolapp.R
import com.mishaismenska.hackatonrsschoolapp.databinding.FragmentAddUserBinding
import com.mishaismenska.hackatonrsschoolapp.di.App
import com.mishaismenska.hackatonrsschoolapp.presentation.viewmodels.AddUserViewModel
import javax.inject.Inject

class AddUserFragment : Fragment() {

    private lateinit var binding: FragmentAddUserBinding
    @Inject
    lateinit var viewModel: AddUserViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddUserBinding.inflate(inflater, container, false)
        val genderNames = resources.getStringArray(R.array.genders_names)
        binding.genderInput.setAdapter(NoFilterAdapter(requireContext(), R.layout.gender_dropdown_item, genderNames))
        binding.genderInput.setText(genderNames.last())
        (requireActivity().application as App).appComponent.inject(this)
        binding.goButton.setOnClickListener {
            if (viewModel.validate(binding)) {
                viewModel.addUser(binding)
            }
        }
        viewModel.isFragmentOpened.observe(viewLifecycleOwner, Observer {
            if (!it) {
                openMainFragment()
            }
        })
        return binding.root
    }

    private fun openMainFragment() {
        parentFragmentManager
            .beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
            .replace(R.id.main_fragment_container, MainFragment())
            .commit()
    }
}
