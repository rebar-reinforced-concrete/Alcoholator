package com.mishaismenska.hackatonrsschoolapp.presentation

import android.content.Context
import android.content.pm.ActivityInfo
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import com.mishaismenska.hackatonrsschoolapp.R
import com.mishaismenska.hackatonrsschoolapp.data.PermissionManager
import com.mishaismenska.hackatonrsschoolapp.databinding.FragmentAddDrinkBinding
import com.mishaismenska.hackatonrsschoolapp.di.App
import com.mishaismenska.hackatonrsschoolapp.presentation.viewmodels.AddDrinkViewModel
import com.mishaismenska.hackatonrsschoolapp.staticPresets.VolumePreset
import javax.inject.Inject


class AddDrinkFragment : Fragment(), AdapterView.OnItemClickListener {

    private lateinit var binding: FragmentAddDrinkBinding

    @Inject
    lateinit var viewModel: AddDrinkViewModel

                    @Inject
                    lateinit var permissionManager: PermissionManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddDrinkBinding.inflate(inflater, container, false)
        (requireActivity().application as App).appComponent.inject(this)
                        permissionManager.checkAndRequestGPSLocationPermissions(this)
        val drinkTypes = resources.getStringArray(R.array.drink_types)
        binding.typeInput.setAdapter(
            NoFilterAdapter(requireContext(), R.layout.drink_type_dropdown_item, drinkTypes)
        )
        binding.volumeInput.setAdapter(
            NoFilterAdapter(requireContext(), R.layout.drink_type_dropdown_item, getVolumeStrings())
        )
        binding.typeInput.keyListener = null
        binding.volumeInput.keyListener = null
        binding.typeInput.onItemClickListener = this
        binding.typeInput.setText(drinkTypes.first())
        binding.volumeInput.setText(binding.volumeInput.adapter.getItem(0).toString())
                        binding.goButton.setOnClickListener {
                            val locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
                            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                                viewModel.requestGPSServices(requireContext())
                            }
                            viewModel.addDrink(binding, locationManager)
                        }
        viewModel.isFragmentOpened.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (!it) parentFragmentManager.popBackStack()
        })
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

                    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
                        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
                        if(grantResults[0] == -1) {
                            viewModel.triggerExplanation(requireContext(), permissionManager::checkAndRequestGPSLocationPermissions, this)
                        }
                    }

    override fun onPause() {
        super.onPause()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
    }

    // TODO: move to view model or use case or i even don't now. It's too. FUCKING TOOOO bad
    private fun getVolumeStrings(): Array<String> =
        resources.getStringArray(R.array.volume_names).toMutableList().mapIndexed { index, s ->
            s.format(
                viewModel.formatter.format(
                    viewModel.convertMeasureIfRequired(VolumePreset.values()[index])
                )
            )
        }.toTypedArray()

    override fun onItemClick(parent: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        val indexes = viewModel.calculateIndexes(parent!!.adapter.getItem(position) as String)
        val volumes: MutableList<String> = getVolumeStrings().toMutableList()
        val names = indexes.map { volumes[it] }
        indexes.map { volumes.removeAt(it) }
        binding.volumeInput.setAdapter(
            NoFilterAdapter(
                requireContext(), R.layout.drink_type_dropdown_item,
                (names + volumes).toTypedArray()
            )
        )
        binding.volumeInput.setText(names.first())
    }
}
