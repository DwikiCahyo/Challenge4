package com.example.noteapp.UI

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
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
    private val viewModel:LoginViewModel by viewModels()
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

        val name = viewModel.getUsernamePreferences("key_username").toString()
        binding.tvWelcome.text = getString(R.string.welcome,name)

        adapter = NoteAdapter(ArrayList())

        //default list
        noteViewModel.getListNoteObserver().observe(viewLifecycleOwner){
            adapter.setData(it as ArrayList<NoteData>)
            setupRecyclerView()
        }

        //filter descending list
        binding.btnDesc.setOnClickListener {
            noteViewModel.getListNoteDesc()
            noteViewModel.getListNoteDescObserver().observe(viewLifecycleOwner){
                adapter.setData(it as ArrayList<NoteData>)
                setupRecyclerView()
            }
        }

        //filter ascending list
        binding.btnAsc.setOnClickListener {
            noteViewModel.getListNote()
            noteViewModel.getListNoteObserver().observe(viewLifecycleOwner){
                adapter.setData(it as ArrayList<NoteData>)
                setupRecyclerView()
            }
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

    private fun setupRecyclerView() {
        binding.rvNote.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        binding.rvNote.adapter = adapter
        Log.d("Main Setup rv","Setup rv")
    }

    fun getAllNote(){
        GlobalScope.launch {
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

}