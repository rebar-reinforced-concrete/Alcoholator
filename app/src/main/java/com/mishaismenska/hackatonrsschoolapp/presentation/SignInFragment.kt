package com.mishaismenska.hackatonrsschoolapp.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.mishaismenska.hackatonrsschoolapp.R
import com.mishaismenska.hackatonrsschoolapp.databinding.FragmentSignInBinding
import com.mishaismenska.hackatonrsschoolapp.di.App
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.AppDataRepository
import com.mishaismenska.hackatonrsschoolapp.presentation.viewmodels.SignInViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class SignInFragment : Fragment(), View.OnClickListener {

    @Inject
    lateinit var viewModel: SignInViewModel

    @Inject
    lateinit var appDataRepository: AppDataRepository

    private lateinit var _binding: FragmentSignInBinding
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        binding.signInButton.setOnClickListener(this)
        (requireActivity().application as App).appComponent.inject(this)
        viewModel.userExistsOnServer.observe(viewLifecycleOwner, Observer {
            openRequiredFragment(it!!)
        })
        return binding.root
    }

    private fun openRequiredFragment(userExists: Boolean) {
        if (userExists) {
            runBlocking {
                launch(Dispatchers.IO) {
                    appDataRepository.synchronizeUserDetails()
                }
            }
            openMainFragment()
        } else
            openAddUserFragment()
    }

    private fun openAddUserFragment() {
        parentFragmentManager
            .beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
            .replace(R.id.main_fragment_container, AddUserFragment())
            .commit()
    }

    override fun onClick(view: View?) {
        startActivityForResult(viewModel.signIn(requireActivity()), 9001) //fixme: find a constant with that shit
        binding.progressBar.visibility = View.VISIBLE
        binding.signInButton.isEnabled = false
    }

    private fun openMainFragment() {
        parentFragmentManager
            .beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
            .replace(R.id.main_fragment_container, MainFragment())
            .commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 9001) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            viewModel.handleSignInResult(task)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
