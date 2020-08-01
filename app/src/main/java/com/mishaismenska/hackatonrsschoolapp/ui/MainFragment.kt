package com.mishaismenska.hackatonrsschoolapp.ui

import android.icu.util.Measure
import android.icu.util.MeasureUnit
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mishaismenska.hackatonrsschoolapp.data.models.Behaviours
import com.mishaismenska.hackatonrsschoolapp.data.models.Drink
import com.mishaismenska.hackatonrsschoolapp.data.models.DrinkType
import com.mishaismenska.hackatonrsschoolapp.data.models.UserState
import com.mishaismenska.hackatonrsschoolapp.databinding.FragmentMainBinding
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.mainRecycler.adapter = DrinksRecyclerAdapter(UserState(0.0, Period.ZERO, Behaviours.STUPOR)).apply {
            drinks = listOf(
                Drink(DrinkType.CHAMPAGNE, LocalDateTime.now(), Measure(122, MeasureUnit.MILLILITER)),
                Drink(DrinkType.COGNAC, LocalDateTime.now(), Measure(500, MeasureUnit.MILLILITER))
            )
        }
        return binding.root
    }
}
