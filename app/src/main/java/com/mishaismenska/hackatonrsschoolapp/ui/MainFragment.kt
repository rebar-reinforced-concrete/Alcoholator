package com.mishaismenska.hackatonrsschoolapp.ui

import android.icu.util.Measure
import android.icu.util.MeasureUnit
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mishaismenska.hackatonrsschoolapp.App
import com.mishaismenska.hackatonrsschoolapp.R
import com.mishaismenska.hackatonrsschoolapp.data.AppDataRepositoryImpl
import com.mishaismenska.hackatonrsschoolapp.data.models.Drink
import com.mishaismenska.hackatonrsschoolapp.data.models.DrinkType
import com.mishaismenska.hackatonrsschoolapp.data.models.Gender
import com.mishaismenska.hackatonrsschoolapp.interfaces.AppDataRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    @Inject
    lateinit var repository: AppDataRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        (requireActivity().application as App).appComponent.inject(this)
        GlobalScope.launch {
            repository.addUser(
                11,
                Measure(8112, MeasureUnit.POUND),
                Gender.FEMALE_IDENTIFIES_AS_MALE
            )
            repository.addDrink(
                Drink(
                    DrinkType.FRUIT_WINE_CHEAP,
                    LocalDateTime.now(),
                    Measure(5430, MeasureUnit.GALLON)
                )
            )
            SystemClock.sleep(5000)
            repository.addDrink(
                Drink(
                    DrinkType.BEER_DARK,
                    LocalDateTime.now(),
                    Measure(52430, MeasureUnit.GALLON)
                )
            )
            val u = repository.getUserWithDrinks()
            Log.d("age", u.age.toString())
            Log.d("weight", u.weight.toString())
            u.gender.name.let { Log.d("gender", it) }
            Log.d("drinks amount", u.drinks.value?.size.toString())
            Log.d("drinks", u.drinks.value?.joinToString(separator = "; ").toString())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
