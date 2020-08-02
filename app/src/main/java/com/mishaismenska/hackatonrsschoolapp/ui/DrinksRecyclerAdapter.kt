package com.mishaismenska.hackatonrsschoolapp.ui

import android.icu.text.MeasureFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mishaismenska.hackatonrsschoolapp.R
import com.mishaismenska.hackatonrsschoolapp.data.models.Drink
import com.mishaismenska.hackatonrsschoolapp.data.models.UserState
import com.mishaismenska.hackatonrsschoolapp.databinding.DrinkRecyclerItemBinding
import com.mishaismenska.hackatonrsschoolapp.databinding.MainInfoCardBinding
import java.util.*

class DrinksRecyclerAdapter(initialUserState: UserState) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var userState: UserState = initialUserState
        set(value) {
            Log.d("LAGAPETTT", "PЭNIS ")
            field = value
            notifyItemChanged(0)
        }

    var drinks = emptyList<Drink>()
        set(value) {
            //val diffUtilCallback = DrinksDiffUtilCallback(field, value)
            //val diffResults = DiffUtil.calculateDiff(diffUtilCallback)
            field = value
            notifyDataSetChanged()
            //diffResults.dispatchUpdatesTo(this)
        }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 0) {
            val binding =
                MainInfoCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return StateViewHolder(binding)
        } else {
            val binding =
                DrinkRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return DrinkViewHolder(
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
            (holder as DrinkViewHolder).bind(drinks[position - 1])
        } else (holder as StateViewHolder).bind(userState)
    }

    class DrinkViewHolder(
        private val binding: DrinkRecyclerItemBinding,
        private val drinkTypes: Array<String>
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(drink: Drink) {
            binding.drinkName.text = drinkTypes[drink.type.ordinal]
            binding.drinkDescription.text =
                MeasureFormat.getInstance(Locale.getDefault(), MeasureFormat.FormatWidth.WIDE)
                    .format(drink.volume)
        }
    }

    class StateViewHolder(private val binding: MainInfoCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(state: UserState) {
            if(state.alcoholConcentration > 0){
                binding.mainInfoCard.visibility = View.VISIBLE
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
            } else binding.mainInfoCard.visibility = View.GONE

        }
    }
}
