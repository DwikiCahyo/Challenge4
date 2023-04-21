package com.example.noteapp.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.noteapp.R
import com.example.noteapp.data.NoteData
import com.example.noteapp.data.NoteDatabase
import com.example.noteapp.databinding.FragmentAddBinding
import com.example.noteapp.databinding.FragmentMainBinding
import com.example.noteapp.viewModel.LoginViewModel
import com.example.noteapp.viewModel.NoteViewModel
import com.example.noteapp.viewModel.StateViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async


class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private val viewModel:NoteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.window!!.statusBarColor = ContextCompat.getColor(requireContext(),R.color.white)
        binding.btnTambah.setOnClickListener {
            addNote()
        }
    }

    fun addNote(){
        val title = binding.edtTitle.text.toString()
        val content = binding.intEditText.text.toString()
        viewModel.insertNote(title,content)
        Toast.makeText(requireContext(),"Berhasil menambahkan catatan",Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_addFragment_to_mainFragment)
    }

}