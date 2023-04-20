package com.example.noteapp.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.R
import com.example.noteapp.UI.MainFragment
import com.example.noteapp.data.NoteData
import com.example.noteapp.data.NoteDatabase
import com.example.noteapp.databinding.ItemNoteBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class NoteAdapter (var note:List<NoteData>):RecyclerView.Adapter<NoteAdapter.ViewHolder>(){

    var DBNote : NoteDatabase? = null


    class ViewHolder(var binding:ItemNoteBinding):RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemNoteBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int  = note.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.note = note[position]

        holder.binding.btnDelete.setOnClickListener {
            DBNote = NoteDatabase.getInstance(it.context)
            GlobalScope.async {
                val delete = DBNote?.noteDao()?.deleteAll(note[position])
                (holder.itemView.context as MainFragment).activity?.runOnUiThread {
                    (holder.itemView.context as MainFragment).getAllNote()
                }
            }
            Navigation.findNavController(it).navigate(R.id.mainFragment)
        }

        holder.binding.btnEdit.setOnClickListener {
                val bundle = Bundle()
                bundle.putParcelable("data", note[position])
                Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_editFragment, bundle)
        }
    }

    fun setData(newNote: ArrayList<NoteData>) {
        this.note = newNote
    }
}