package com.mishaismenska.hackatonrsschoolapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.mishaismenska.hackatonrsschoolapp.App
import com.mishaismenska.hackatonrsschoolapp.R
import com.mishaismenska.hackatonrsschoolapp.data.models.Behaviours
import com.mishaismenska.hackatonrsschoolapp.data.models.Drink
import com.mishaismenska.hackatonrsschoolapp.data.models.User
import com.mishaismenska.hackatonrsschoolapp.data.models.UserState
import com.mishaismenska.hackatonrsschoolapp.databinding.FragmentMainBinding
import com.mishaismenska.hackatonrsschoolapp.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.view.*
import java.time.Duration
import javax.inject.Inject

class MainFragment : Fragment(), DbResultsListener {

    @Inject
    lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentMainBinding
    private lateinit var drinksAdapter: DrinksRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as App).appComponent.inject(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.getUser(this)
        binding = FragmentMainBinding.inflate(inflater, container, false)
        drinksAdapter = DrinksRecyclerAdapter(UserState(0.0, Duration.ZERO, Behaviours.STUPOR))
        binding.mainRecycler.adapter = drinksAdapter
        binding.addDrinkFab.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, AddDrinkFragment()).addToBackStack(null)
                .commit()
        }
        return binding.root
    }

    private fun checkIfEmpty(data: List<Drink>?) : Boolean {
        return if (data.isNullOrEmpty()) {
            binding.mainRecycler.visibility = View.GONE
            binding.mainRecyclerEmptyTextView.visibility = View.VISIBLE
            true
        } else {
            binding.mainRecycler.visibility = View.VISIBLE
            binding.mainRecyclerEmptyTextView.visibility = View.GONE
            false
        }
    }

    override fun onUserLoaded(user: User) {
        user.drinks.observe(viewLifecycleOwner, Observer { it ->
            if(!checkIfEmpty(it)){
                drinksAdapter.drinks = it
                viewModel.updateState()
            }
            viewModel.userState.observe(viewLifecycleOwner, Observer { state ->
                drinksAdapter.userState = state
            })
        })
    }
}
