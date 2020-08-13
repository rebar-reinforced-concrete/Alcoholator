package com.mishaismenska.hackatonrsschoolapp.ui

import android.os.Bundle
import android.view.*
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.mishaismenska.hackatonrsschoolapp.App
import com.mishaismenska.hackatonrsschoolapp.R
import com.mishaismenska.hackatonrsschoolapp.data.staticPresets.Behaviours
import com.mishaismenska.hackatonrsschoolapp.data.models.Drink
import com.mishaismenska.hackatonrsschoolapp.data.models.User
import com.mishaismenska.hackatonrsschoolapp.data.models.UserState
import com.mishaismenska.hackatonrsschoolapp.databinding.FragmentMainBinding
import com.mishaismenska.hackatonrsschoolapp.interfaces.AppNotificationManager
import com.mishaismenska.hackatonrsschoolapp.viewmodels.MainViewModel
import java.time.Duration
import javax.inject.Inject

class MainFragment : Fragment(), DbResultsListener {

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
        viewModel.getUser(this)
        binding = FragmentMainBinding.inflate(inflater, container, false)
        viewModel.isAddDrinkFabVisible.observe(viewLifecycleOwner, Observer {
            //TODO: show message about drunkenness state
            if (it != null && it) {
                binding.addDrinkFab.visibility = View.VISIBLE
            } else {
                binding.addDrinkFab.visibility = View.INVISIBLE
            }
        })
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        drinksAdapter = DrinksRecyclerAdapter(
            UserState(0.0, Duration.ZERO, Behaviours.SOBER)
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
    private fun checkIfEmpty(data: List<Drink>?) {
        return if (data.isNullOrEmpty()) {
            binding.mainRecycler.visibility = View.GONE
            binding.mainRecyclerEmptyTextView.visibility = View.VISIBLE
        } else {
            binding.mainRecycler.visibility = View.VISIBLE
            binding.mainRecyclerEmptyTextView.visibility = View.GONE
        }
    }

    //TODO: replace with LiveData<User>
    override fun onUserLoaded(user: User) {
        user.drinks.observe(viewLifecycleOwner, Observer { it ->
            viewModel.createState() //unnecessary here. remove
            checkIfEmpty(it)
            drinksAdapter.drinks = it
        })
        viewModel.userState.observe(viewLifecycleOwner, Observer { state ->
            drinksAdapter.userState = state
        })
    }
}

