package com.mishaismenska.hackatonrsschoolapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.GetDrinkingPlacesUseCase
import com.mishaismenska.hackatonrsschoolapp.presentation.models.LocationUIModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MapsViewModel @Inject constructor(private val getDrinkingPlacesUseCase: GetDrinkingPlacesUseCase) : ViewModel() {

    private var _locationsLiveData: MutableLiveData<List<LocationUIModel>>? = null
    val locationsLiveData: LiveData<List<LocationUIModel>> get() = _locationsLiveData as LiveData<List<LocationUIModel>>

    private var _drinksForLocation: MutableLiveData<List<String>> = MutableLiveData(listOf())
    val drinksForLocation: LiveData<List<String>> get() = _drinksForLocation

    fun displayDrinkingPlaces() { // fixme, it looks baaaaad
        _locationsLiveData = MutableLiveData(listOf())
        viewModelScope.launch(Dispatchers.IO) {
            val locations = getDrinkingPlacesUseCase.getPlaces()
            locations.collect {
                _locationsLiveData!!.postValue(it.filterNotNull())
            }
        }
    }

    fun getDrinksForLocation(location: LatLng) {
        viewModelScope.launch(Dispatchers.IO) {
            val locations = getDrinkingPlacesUseCase.getPlaces()
            locations.collect {
                _drinksForLocation.postValue(it.filterNotNull().filter { item -> item.lat == location.latitude && item.long == location.longitude }
                    .map { item -> item.title })
            }
        }
    }
}
