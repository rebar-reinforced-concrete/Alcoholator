package com.mishaismenska.hackatonrsschoolapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.preference.PreferenceManager
import com.google.android.material.snackbar.Snackbar
import com.mishaismenska.hackatonrsschoolapp.R
import com.mishaismenska.hackatonrsschoolapp.databinding.FragmentMainBinding
import com.mishaismenska.hackatonrsschoolapp.di.App
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.ConvertIfRequiredUseCase
import com.mishaismenska.hackatonrsschoolapp.presentation.interfaces.AppNotificationManager
import com.mishaismenska.hackatonrsschoolapp.presentation.models.UserStateUIModel
import com.mishaismenska.hackatonrsschoolapp.presentation.viewmodels.MainViewModel
import com.mishaismenska.hackatonrsschoolapp.staticPresets.Behavior
import java.time.Duration
import javax.inject.Inject

class MainFragment : Fragment() {

    @Inject
    lateinit var viewModel: MainViewModel
    @Inject
    lateinit var convertIfRequiredUseCase: ConvertIfRequiredUseCase
    @Inject
    lateinit var notificationManager: AppNotificationManager
    private lateinit var binding: FragmentMainBinding
    private lateinit var drinksAdapter: DrinksRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as App).appComponent.inject(this)
        requireActivity().runOnUiThread {
            (requireActivity() as MainActivity).showBottomNavigation()
        }
        viewModel.getDrinksAndUser()
        drinksAdapter = DrinksRecyclerAdapter(convertIfRequiredUseCase, UserStateUIModel(0.0, Duration.ZERO, Behavior.SOBER))
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.mainRecycler.adapter = drinksAdapter
        viewModel.drinks.observe(viewLifecycleOwner, Observer {
            drinksAdapter.drinks = it
        })
        viewModel.userState.observe(viewLifecycleOwner, Observer { state ->
            drinksAdapter.userState = state
        })
        viewModel.isAddDrinkFabVisible.observe(viewLifecycleOwner, Observer {
            if (binding.addDrinkFab.visibility == View.VISIBLE && !it) {
                val name = PreferenceManager.getDefaultSharedPreferences(context)
                    .getString(requireContext().getString(R.string.name_key), getString(R.string.fella))
                Snackbar.make(binding.root, getString(R.string.too_drunk, name), Snackbar.LENGTH_LONG).show()
            }
            binding.addDrinkFab.visibility = if (it) View.VISIBLE else View.GONE
        })
        viewModel.isRecyclerVisible.observe(viewLifecycleOwner, Observer {
            binding.mainRecycler.visibility = if (it) View.VISIBLE else View.GONE
        })
        viewModel.isEmptyRecyclerTextViewVisible.observe(viewLifecycleOwner, Observer {
            binding.mainRecyclerEmptyTextView.visibility = if (it) View.VISIBLE else View.GONE
        })
        binding.addDrinkFab.setOnClickListener {
            openAddDrinkFragment()
        }
        setHasOptionsMenu(true)
        return binding.root
    }


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
}
