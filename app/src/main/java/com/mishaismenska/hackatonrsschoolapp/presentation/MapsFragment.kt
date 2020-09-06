package com.mishaismenska.hackatonrsschoolapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import com.mishaismenska.hackatonrsschoolapp.R
import com.mishaismenska.hackatonrsschoolapp.databinding.FragmentMapsBinding
import com.mishaismenska.hackatonrsschoolapp.di.App
import com.mishaismenska.hackatonrsschoolapp.presentation.models.LocationUIModel
import com.mishaismenska.hackatonrsschoolapp.presentation.viewmodels.MapsViewModel
import javax.inject.Inject
import kotlinx.android.synthetic.main.bottom_sheet_drinks.*

class MapsFragment : Fragment(), GoogleMap.OnMarkerClickListener {
    private lateinit var binding: FragmentMapsBinding

    @Inject
    lateinit var viewModel: MapsViewModel
    private lateinit var mapFragment: SupportMapFragment
    private var behavior: BottomSheetBehavior<LinearLayout>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity().application as App).appComponent.inject(this)
        binding = FragmentMapsBinding.inflate(inflater, container, false)
        behavior = BottomSheetBehavior.from(binding.drinksBs.root)
        behavior!!.state = BottomSheetBehavior.STATE_HIDDEN
        return binding.root
    }

    private fun addMarker(location: LocationUIModel) {
        mapFragment.getMapAsync {
            val currentLatLong = LatLng(location.lat, location.long)
            it.addMarker(MarkerOptions().position(currentLatLong).title(location.dateTimeTaken))
            it.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong, 16.0f))
            it.setOnMarkerClickListener(this)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.displayDrinkingPlaces()
        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        viewModel.locationsLiveData.observe(viewLifecycleOwner, Observer {
            it.map { location ->
                addMarker(location)
                viewModel.getDrinksForLocation(LatLng(location.lat, location.long))
            }
        })
        viewModel.drinksForLocation.observe(viewLifecycleOwner, Observer {
            binding.drinksBs.bsChips.removeAllViews()
            it.map {
                binding.drinksBs.bsChips.addView(
                    (layoutInflater.inflate(R.layout.drink_bs_item, binding.drinksBs.bsChips, false) as Chip).apply { text = it })
            }
            behavior!!.state = BottomSheetBehavior.STATE_EXPANDED
        })
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        viewModel.getDrinksForLocation(marker!!.position)
        return true
    }
}
