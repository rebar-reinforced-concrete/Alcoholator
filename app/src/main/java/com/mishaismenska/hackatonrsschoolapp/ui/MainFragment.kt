package com.mishaismenska.hackatonrsschoolapp.ui

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.preference.PreferenceManager
import com.google.android.material.snackbar.Snackbar
import com.mishaismenska.hackatonrsschoolapp.App
import com.mishaismenska.hackatonrsschoolapp.R
import com.mishaismenska.hackatonrsschoolapp.data.staticPresets.Behaviours
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
    private val alarmManager by lazy { (requireContext().getSystemService(ALARM_SERVICE) as AlarmManager) }
    private val pendingIntent: PendingIntent by lazy {
        PendingIntent.getBroadcast(
            context,
            1,
            Intent(context, ScheduledBroadcastReceiver::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

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
        drinksAdapter = DrinksRecyclerAdapter(
            UserState(0.0, Duration.ZERO, Behaviours.SOBER)
        )
        binding.mainRecycler.adapter = drinksAdapter
        binding.addDrinkFab.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, AddDrinkFragment()).setTransition(
                FragmentTransaction.TRANSIT_FRAGMENT_OPEN).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null)
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
                    .replace(R.id.main_fragment_container, AppSettingsFragment()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(null).commit()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun checkIfEmpty(data: List<Drink>?): Boolean {
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

    private var drinksChanged = false
    override fun onUserLoaded(user: User) {
        user.drinks.observe(viewLifecycleOwner, Observer { it ->
            if (!checkIfEmpty(it)) {
                drinksAdapter.drinks = it
                viewModel.updateState()
                drinksChanged = true
            } else binding.addDrinkFab.visibility = View.VISIBLE
            viewModel.userState.observe(viewLifecycleOwner, Observer { state ->
                drinksAdapter.userState = state
                if(state.alcoholConcentration > 11.349) {
                    if(binding.addDrinkFab.visibility != View.GONE) {
                        binding.addDrinkFab.visibility = View.GONE
                        val name = PreferenceManager.getDefaultSharedPreferences(context).getString(requireContext().getString(R.string.name_key), "fella")
                        Snackbar.make(binding.root, getString(R.string.too_drunk, name), Snackbar.LENGTH_LONG).show()
                    }
                } else{
                    binding.addDrinkFab.visibility = View.VISIBLE
                }
                if (state != null && state.alcoholConcentration > 0.001 && drinksChanged) {
                    val timeToTrigger =
                        System.currentTimeMillis() + viewModel.userState.value!!.soberTime.toMillis()
                    alarmManager.cancel(pendingIntent)
                    alarmManager.setAlarmClock(
                        AlarmManager.AlarmClockInfo(
                            timeToTrigger,
                            pendingIntent
                        ), pendingIntent
                    )
                    drinksChanged = false
                }
            })
        })
    }
}

