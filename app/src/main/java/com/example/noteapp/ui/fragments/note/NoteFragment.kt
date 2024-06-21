package com.example.noteapp.ui.fragments.note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteapp.App
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentNoteBinding
import com.example.noteapp.databinding.FragmentOnBoardBinding
import com.example.noteapp.ui.adapters.NoteAdapter
import com.example.noteapp.ui.data.extension.getBackStackData
import com.example.noteapp.ui.data.models.NoteModel

class NoteFragment : Fragment() {

    private lateinit var binding: FragmentNoteBinding
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteAdapter = NoteAdapter()
        initialize()
        setupListeners()
        getData()
    }

    private fun initialize() {
        binding.rvNote.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = noteAdapter
        }
    }

    private fun setupListeners() = with(binding) {
        addBtn.setOnClickListener{
            findNavController().navigate(R.id.action_noteFragment_to_detailNoteFragment)
        }
    }

    private fun getData() {
       App().getInstance()?.noteDao()?.getAll()?.observe(viewLifecycleOwner){
           noteAdapter.submitList(it)
       }
    }
}