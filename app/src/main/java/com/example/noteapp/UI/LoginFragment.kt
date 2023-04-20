package com.example.noteapp.UI

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentLoginBinding
import com.example.noteapp.viewModel.LoginViewModel
import com.example.noteapp.viewModel.StateViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var usernameConfirm:String
    private lateinit var passwordConfirm:String
    private val viewModel: LoginViewModel by viewModels()
    private val stateViewModel:StateViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setting status bar
        activity?.window!!.statusBarColor = ContextCompat.getColor(requireContext(),R.color.white)
        // setup shared pref
        usernameConfirm = viewModel.getUsernamePreferences("key_username").toString()
        passwordConfirm = viewModel.getPasswordPreferences("key_password").toString()

        Log.d("viewmodel username", usernameConfirm)
        Log.d("viewmodel password",passwordConfirm)

        getLogin()
        getRegister()

    }

    private fun getRegister() {
        binding.tvBtnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun getLogin() {
        binding.btnLogin.setOnClickListener {

            val username = binding.edtNamaPengguna.text.toString()
            val password = binding.edtPassword.text.toString()

            if (username == usernameConfirm && password == passwordConfirm) {
                stateViewModel.saveLoginState(true)
                Toast.makeText(requireContext(), "Login Berhasil", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_loginFragment_to_mainFragment)

            } else {
                Toast.makeText(requireContext(), "Login Gagal", Toast.LENGTH_SHORT).show()
                Log.e("Login Username", usernameConfirm)
                Log.e("Login Pass", passwordConfirm)
            }

        }
    }

}