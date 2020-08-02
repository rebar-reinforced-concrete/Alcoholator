package com.mishaismenska.hackatonrsschoolapp.ui

import android.content.SharedPreferences
import android.icu.text.MeasureFormat
import android.icu.util.Measure
import android.icu.util.MeasureUnit
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mishaismenska.hackatonrsschoolapp.R
import com.mishaismenska.hackatonrsschoolapp.data.mlToOz
import com.mishaismenska.hackatonrsschoolapp.data.models.Drink
import com.mishaismenska.hackatonrsschoolapp.data.models.UserState
import com.mishaismenska.hackatonrsschoolapp.databinding.DrinkRecyclerItemBinding
import com.mishaismenska.hackatonrsschoolapp.databinding.MainInfoCardBinding
import java.util.*

class DrinksRecyclerAdapter(
    initialUserState: UserState,
    private val preferences: SharedPreferences
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var userState: UserState = initialUserState
        set(value) {
            field = value
            notifyItemChanged(0)
        }

    var drinks = emptyList<Drink>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            val binding =
                MainInfoCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            StateViewHolder(binding)
        } else {
            val binding =
                DrinkRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            DrinkViewHolder(
                binding,
                parent.context.resources.getStringArray(R.array.drink_types)
            )
        }
    }

    override fun getItemCount(): Int {
        return drinks.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position >= 1) {
            (holder as DrinkViewHolder).bind(
                drinks[position - 1],
                preferences.getBoolean("units", false)
            )
        } else (holder as StateViewHolder).bind(userState)
    }

    class DrinkViewHolder(
        private val binding: DrinkRecyclerItemBinding,
        private val drinkTypes: Array<String>
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(drink: Drink, unit: Boolean) {
            binding.drinkName.text = drinkTypes[drink.type.ordinal]
            binding.drinkDescription.text =
                if (unit)
                    MeasureFormat.getInstance(Locale.getDefault(), MeasureFormat.FormatWidth.WIDE)
                        .format(
                            Measure(
                                mlToOz(drink.volume.number as Int),
                                MeasureUnit.FLUID_OUNCE
                            )
                        )
                else
                    MeasureFormat.getInstance(Locale.getDefault(), MeasureFormat.FormatWidth.WIDE)
                        .format(drink.volume)
        }
    }

    class StateViewHolder(private val binding: MainInfoCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(state: UserState) {
            val resources = binding.root.context.resources
            val states = resources.getStringArray(R.array.drunk_states)
            binding.soberingTimer.text = resources.getString(
                R.string.time_format,
                state.soberTime.toHours(),
                state.soberTime.toMinutes() % 60
            )
            binding.alcoholConcentration.text = resources.getString(
                R.string.concentration_format, "%.2f".format(state.alcoholConcentration)
            )
            binding.stateDescription.text = states[state.behaviour.ordinal]
        }
    }
}
