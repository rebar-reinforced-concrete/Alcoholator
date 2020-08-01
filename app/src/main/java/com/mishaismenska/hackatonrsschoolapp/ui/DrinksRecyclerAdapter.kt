package com.mishaismenska.hackatonrsschoolapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mishaismenska.hackatonrsschoolapp.R
import com.mishaismenska.hackatonrsschoolapp.data.models.Drink
import com.mishaismenska.hackatonrsschoolapp.data.models.DrinkType
import com.mishaismenska.hackatonrsschoolapp.data.models.UserState
import com.mishaismenska.hackatonrsschoolapp.databinding.DrinkRecyclerItemBinding
import com.mishaismenska.hackatonrsschoolapp.databinding.MainInfoCardBinding

class DrinksRecyclerAdapter(initialUserState: UserState) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var userState : UserState = initialUserState
    set(value) {
        field = value
        notifyItemChanged(0)
    }

    var drinks = emptyList<Drink>()
    set(value){
        field = value
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == 0){
            val binding = MainInfoCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return StateViewHolder(binding)
        }
        else {
            val binding = DrinkRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return DrinkViewHolder(binding, parent.context.resources.getStringArray(R.array.drink_types))
        }
    }

    override fun getItemCount(): Int {
        return drinks.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(position >= 1){
            (holder as DrinkViewHolder).bind(drinks[position - 1])
        }
    }

    class DrinkViewHolder(private val binding: DrinkRecyclerItemBinding, private val drinkTypes: Array<String>) : RecyclerView.ViewHolder(binding.root){
        fun bind(drink: Drink){

            binding.drinkName.text = drinkTypes[drink.type.ordinal]
            binding.drinkDescription.text = drink.volume.number.toString() + drink.volume.unit.subtype.toString()
        }
    }

    class StateViewHolder(val binding: MainInfoCardBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(){

        }
    }
}
