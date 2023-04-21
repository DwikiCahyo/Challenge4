package com.example.noteapp.UI

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteapp.R
import com.example.noteapp.adapter.NoteAdapter
import com.example.noteapp.data.NoteData
import com.example.noteapp.data.NoteDatabase
import com.example.noteapp.databinding.FragmentMainBinding
import com.example.noteapp.viewModel.LoginViewModel
import com.example.noteapp.viewModel.NoteViewModel
import com.example.noteapp.viewModel.StateViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel:LoginViewModel by viewModels()
    private val stateViewModel:StateViewModel by viewModels()
    private val noteViewModel:NoteViewModel by viewModels()
    private lateinit var adapter:NoteAdapter
    var NoteDB : NoteDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = loginViewModel.getUsernamePreferences("key_username").toString()
        binding.tvWelcome.text = getString(R.string.welcome,name)

        activity?.window!!.statusBarColor = ContextCompat.getColor(requireContext(),R.color.white)

        adapter = NoteAdapter(ArrayList())

        //check sort state
        stateViewModel.getSortState().observe(viewLifecycleOwner){ state  ->
            Log.d(TAG,"Sort state $state")
            if (state) descendingList() else defaultList()
        }

        binding.btnAsc.setOnClickListener {
            stateViewModel.saveSortState(false)
            defaultList()
        }

        binding.btnDesc.setOnClickListener {
            stateViewModel.saveSortState(true)
            descendingList()
        }

        //logout
        binding.btnLogout.setOnClickListener {
            stateViewModel.logout()
            findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
        }


        //add new note
        binding.btnNewNote.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_addFragment)
        }

    }

    private fun descendingList() {
        noteViewModel.getListNoteDesc()
        noteViewModel.getListNoteDescObserver().observe(viewLifecycleOwner) {
            adapter.setData(it as ArrayList<NoteData>)
            setupRecyclerView()
        }
    }

    private fun defaultList() {
        noteViewModel.getListNote()
        noteViewModel.getListNoteObserver().observe(viewLifecycleOwner) {
            adapter.setData(it as ArrayList<NoteData>)
            setupRecyclerView()
        }
    }

    private fun setupRecyclerView() {
        binding.rvNote.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        binding.rvNote.adapter = adapter
        Log.d(TAG,"Setup rv")
    }

    fun getAllNote(){
        lifecycleScope.launch {
            var data = NoteDB?.noteDao()?.getNoteData()
            activity?.runOnUiThread{
                binding.rvNote.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                binding.rvNote.adapter = adapter
            }
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        const val TAG  = "MainFragment"
    }


}