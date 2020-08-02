package com.mishaismenska.hackatonrsschoolapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.mishaismenska.hackatonrsschoolapp.App
import com.mishaismenska.hackatonrsschoolapp.data.models.Behaviours
import com.mishaismenska.hackatonrsschoolapp.data.models.Drink
import com.mishaismenska.hackatonrsschoolapp.data.models.User
import com.mishaismenska.hackatonrsschoolapp.data.models.UserState
import com.mishaismenska.hackatonrsschoolapp.databinding.FragmentMainBinding
import com.mishaismenska.hackatonrsschoolapp.viewmodels.MainViewModel
import java.time.Duration
import javax.inject.Inject

class MainFragment : Fragment(), DbResultsListener {

    @Inject
    lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentMainBinding
    private lateinit var drinksAdapter: DrinksRecyclerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity().application as App).appComponent.inject(this)
        binding = FragmentMainBinding.inflate(inflater, container, false)
        drinksAdapter = DrinksRecyclerAdapter(UserState(0.0, Duration.ZERO, Behaviours.STUPOR))
        binding.mainRecycler.adapter = drinksAdapter
        viewModel.getUser(this)
        return binding.root
    }

    private fun checkIfEmpty(data: List<Drink>) {
        if (data.isEmpty()) {
            binding.mainRecycler.visibility = View.GONE
            binding.mainRecyclerEmptyTextView.visibility = View.VISIBLE
        } else {
            binding.mainRecycler.visibility = View.VISIBLE
            binding.mainRecyclerEmptyTextView.visibility = View.GONE
        }
    }

    override fun onUserLoaded(user: User) {
        user.drinks.observe(viewLifecycleOwner, Observer { it ->
            if (it != null) {
                checkIfEmpty(it)
                drinksAdapter.drinks = it
            }
            viewModel.userState.observe(viewLifecycleOwner, Observer { state ->
                drinksAdapter.userState = state
            })
        })
    }
}
