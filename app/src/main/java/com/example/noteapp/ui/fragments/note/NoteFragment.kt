package com.example.noteapp.ui.fragments.note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteapp.App
import com.example.noteapp.Interface.OnClickItem
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentNoteBinding
import com.example.noteapp.databinding.FragmentOnBoardBinding
import com.example.noteapp.ui.adapters.NoteAdapter
import com.example.noteapp.ui.data.extension.getBackStackData
import com.example.noteapp.ui.data.models.NoteModel
import com.example.noteapp.utils.SharedPreference

class NoteFragment : Fragment(), OnClickItem {

    private lateinit var binding: FragmentNoteBinding
    private lateinit var noteAdapter: NoteAdapter
    private var isGridLayout: Boolean = false
    private lateinit var sharedPreference: SharedPreference
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreference = SharedPreference(requireContext())
        noteAdapter = NoteAdapter(onLongClick = this, onClick = this)
        isGridLayout = sharedPreference.getIsGridLayout()
        initialize()
        setupListeners()
        getData()
        updateLayoutButtonIcon()
        setupDrawer()
    }

    private fun initialize() {
        binding.rvNote.apply {
            layoutManager = if (isGridLayout){
                GridLayoutManager(requireContext(), 2)
            } else {
                LinearLayoutManager(requireContext())
            }
            adapter = noteAdapter
        }
    }

    private fun setupListeners() = with(binding) {
        addBtn.setOnClickListener{
            findNavController().navigate(R.id.action_noteFragment_to_detailNoteFragment)
        }
        gridLayout.setOnClickListener {
            isGridLayout = true
            rvNote.layoutManager = GridLayoutManager(requireContext(), 2)
            sharedPreference.setIsGridLayout(isGridLayout)
            updateLayoutButtonIcon()
        }
        linearLayout.setOnClickListener {
            isGridLayout = false
            rvNote.layoutManager = LinearLayoutManager(requireContext())
            sharedPreference.setIsGridLayout(isGridLayout)
            updateLayoutButtonIcon()
        }
    }

    private fun updateLayoutButtonIcon() = with(binding){
        if (isGridLayout){
            gridLayout.visibility = View.GONE
            linearLayout.visibility = View.VISIBLE
        } else {
            gridLayout.visibility = View.VISIBLE
            linearLayout.visibility = View.GONE
        }
    }

    private fun getData() {
       App().getInstance()?.noteDao()?.getAll()?.observe(viewLifecycleOwner){
           noteAdapter.submitList(it)
       }
    }

    private fun setupDrawer() = with(binding) {
        val drawerLayout = drawerLayout
        val navView = navView
        toggle = ActionBarDrawerToggle(
            activity,
            drawerLayout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        menu.setOnClickListener {
            drawerLayout.openDrawer(navView)
        }

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    findNavController().navigate(R.id.chatFragment)
                }
            }
            drawerLayout.closeDrawer(navView)
            true
        }
    }

    override fun onLongClick(noteModel: NoteModel){
        val builder = AlertDialog.Builder(requireContext())
        with(builder){
            setTitle("Вы точно хотите удалить заметку?")
            setPositiveButton("Да"){ dialog, which ->
                App().getInstance()?.noteDao()?.deleteNote(noteModel)
            }
            setNegativeButton("Нет"){ dialog, which ->
                dialog.cancel()
            }
            show()
        }
        builder.create()
    }

    override fun onClick(noteModel: NoteModel){
        val action = NoteFragmentDirections.actionNoteFragmentToDetailNoteFragment(noteModel.id)
        findNavController().navigate(action)
    }
}