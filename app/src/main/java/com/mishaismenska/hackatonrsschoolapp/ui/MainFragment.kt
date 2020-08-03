package com.mishaismenska.hackatonrsschoolapp.ui

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.preference.PreferenceManager
import com.mishaismenska.hackatonrsschoolapp.App
import com.mishaismenska.hackatonrsschoolapp.R
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
        drinksAdapter = DrinksRecyclerAdapter(
            UserState(0.0, Duration.ZERO, Behaviours.SOBER))
        binding.mainRecycler.adapter = drinksAdapter
        viewModel.getUser(this)
        binding.addDrinkFab.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, AddDrinkFragment()).addToBackStack(null)
                .commit()
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_prefs, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_container, AppSettingsFragment())
                    .addToBackStack(null).commit()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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
