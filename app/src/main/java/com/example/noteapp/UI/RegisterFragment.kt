package com.example.noteapp.UI

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentLoginBinding
import com.example.noteapp.databinding.FragmentRegisterBinding
import com.example.noteapp.viewModel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getRegister()
    }

    private fun getRegister() {
        binding.btnRegister.setOnClickListener {

            val username = binding.edtNamaPengguna.text.toString()
            val password = binding.edtPassword.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Email dan password tidak boleh kosong",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                viewModel.saveUsernamePreferences("key_username", username)
                viewModel.savePasswordPreferences("key_password", password)

                Toast.makeText(requireContext(), "Berhasil, Silahkan login ", Toast.LENGTH_SHORT)
                    .show()
                Log.d("Regis Username", username)
                Log.d("regis password", password)
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
        }
    }

}