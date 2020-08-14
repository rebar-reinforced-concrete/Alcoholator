package com.mishaismenska.hackatonrsschoolapp.presentation

import android.os.Bundle
import android.view.*
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.mishaismenska.hackatonrsschoolapp.di.App
import com.mishaismenska.hackatonrsschoolapp.R
import com.mishaismenska.hackatonrsschoolapp.staticPresets.Behaviours
import com.mishaismenska.hackatonrsschoolapp.domain.models.DrinkDomainModel
import com.mishaismenska.hackatonrsschoolapp.domain.models.UserDomainModel
import com.mishaismenska.hackatonrsschoolapp.domain.models.UserStateDomainModel
import com.mishaismenska.hackatonrsschoolapp.databinding.FragmentMainBinding
import com.mishaismenska.hackatonrsschoolapp.presentation.interfaces.AppNotificationManager
import com.mishaismenska.hackatonrsschoolapp.presentation.models.DrinkUIModel
import com.mishaismenska.hackatonrsschoolapp.presentation.models.UserStateUIModel
import com.mishaismenska.hackatonrsschoolapp.presentation.viewmodels.MainViewModel
import java.time.Duration
import javax.inject.Inject

class MainFragment : Fragment() {

    @Inject
    lateinit var viewModel: MainViewModel

    @Inject
    lateinit var notificationManager: AppNotificationManager
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
        binding = FragmentMainBinding.inflate(inflater, container, false)
        /*viewModel.isAddDrinkFabVisible.observe(viewLifecycleOwner, Observer {
            //TODO: show message about drunkenness state
            if (it != null && it) {
                binding.addDrinkFab.visibility = View.VISIBLE
            } else {
                binding.addDrinkFab.visibility = View.INVISIBLE
            }
        })*/
        binding.addDrinkFab.visibility = View.VISIBLE
        viewModel.getDrinks()
        viewModel.drinks.observe(viewLifecycleOwner, Observer {
            checkIfEmpty(it)
            drinksAdapter.drinks = it
        })
        viewModel.userState.observe(viewLifecycleOwner, Observer { state ->
            drinksAdapter.userState = state
        })
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        drinksAdapter = DrinksRecyclerAdapter(
            UserStateUIModel(0.0, Duration.ZERO, Behaviours.SOBER)
        )
        binding.mainRecycler.adapter = drinksAdapter
        binding.addDrinkFab.setOnClickListener {
            openAddDrinkFragment()
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //unnecessary stuff, replace with navigation
    private fun openAddDrinkFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, AddDrinkFragment()).setTransition(
                FragmentTransaction.TRANSIT_FRAGMENT_OPEN
            ).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null)
            .commit()
    }

    private fun openSettings() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, AppSettingsFragment())
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .addToBackStack(null).commit()
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_prefs, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                openSettings()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    //TODO: move to view model
    private fun checkIfEmpty(data: List<DrinkUIModel>?) {
        return if (data.isNullOrEmpty()) {
            binding.mainRecycler.visibility = View.GONE
            binding.mainRecyclerEmptyTextView.visibility = View.VISIBLE
        } else {
            binding.mainRecycler.visibility = View.VISIBLE
            binding.mainRecyclerEmptyTextView.visibility = View.GONE
        }
    }
}

