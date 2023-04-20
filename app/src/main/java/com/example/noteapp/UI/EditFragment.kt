package com.example.noteapp.UI

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.noteapp.R
import com.example.noteapp.data.NoteData
import com.example.noteapp.databinding.FragmentAddBinding
import com.example.noteapp.databinding.FragmentEditBinding
import com.example.noteapp.viewModel.NoteViewModel

class EditFragment : Fragment() {

    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!
    private val viewModel:NoteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
       _binding = FragmentEditBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataNote = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable("data", NoteData::class.java)
        } else {
            arguments?.getParcelable("data")
        }

       binding.edtTitle.setText(dataNote?.titleNote ?: "Kosong")
       binding.intEditText.setText(dataNote?.contentNote ?: "Tidak ada isi")
       binding.tvId.text = dataNote?.id.toString()

       binding.btnEdit.setOnClickListener {
           val id = binding.tvId.text.toString().toInt()
           val title = binding.edtTitle.text.toString()
           val context = binding.intEditText.text.toString()
           viewModel.updateNote(id, title, context)
           Toast.makeText(requireContext(), "Berhasil diubah", Toast.LENGTH_SHORT).show()
           findNavController().navigate(R.id.action_editFragment_to_mainFragment)
       }
    }

}