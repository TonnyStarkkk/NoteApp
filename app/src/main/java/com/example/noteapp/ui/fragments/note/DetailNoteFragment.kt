package com.example.noteapp.ui.fragments.note

import android.icu.text.DateFormat
import android.icu.util.LocaleData
import android.os.Build
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.example.noteapp.App
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentDetailNoteBinding
import com.example.noteapp.ui.data.extension.setBackStackData
import com.example.noteapp.ui.data.models.NoteModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DetailNoteFragment : Fragment() {

    private lateinit var binding: FragmentDetailNoteBinding
    private var colorResource: Int = R.drawable.style
    private var noteId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailNoteBinding.inflate(inflater, container, false)
        return binding.root
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        update()
        setupListeners()
        displayCurrentDataTime()
    }

    private fun update() = with(binding) {
        arguments?.let{
            noteId = it.getInt("noteId", -1)
        }
        if (noteId != -1){
            val note = App().getInstance()?.noteDao()?.getNoteById(noteId)
            note?.let { model ->
                etTitle.setText(model.title)
                etDescription.setText(model.description)
                colorResource = model.color.toInt()
                when(colorResource) {
                    R.drawable.style -> rb1.isChecked = true
                    R.drawable.style_white -> rb2.isChecked = true
                    R.drawable.style_red -> rb3.isChecked = true
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupListeners() = with(binding) {
        val textWatcher = object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                btnAdd.visibility =
                    if (etTitle.text.isNotEmpty() || etDescription.text.isNotEmpty()){
                        View.VISIBLE
                    } else {
                        View.GONE
                    }
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        etTitle.addTextChangedListener(textWatcher)
        etDescription.addTextChangedListener(textWatcher)

        btnAdd.setOnClickListener{
            val etTitle = etTitle.text.toString()
            val etDescription = etDescription.text.toString()
            val itemData = date.text.toString()
            val itemTime = time.text.toString()

            val noteModel = NoteModel(
                title = etTitle,
                description = etDescription,
                date = itemData,
                time = itemTime,
                color = colorResource.toString()
            )

                    if (noteId != -1){
                        noteModel.id = noteId
                        App().getInstance()?.noteDao()?.updateNote(noteModel)
                    } else {
                        App().getInstance()?.noteDao()?.insertNote(noteModel)
                    }
            findNavController().navigateUp()
        }

        returnBtn.setOnClickListener{
            findNavController().navigate(R.id.action_detailNoteFragment_to_noteFragment)
        }
        btnAdd.visibility = View.GONE

        radioGroup.setOnCheckedChangeListener{ group, checkedId ->
            colorResource = when(checkedId) {
                rb1.id -> R.drawable.style
                rb2.id -> R.drawable.style_white
                rb3.id -> R.drawable.style_red
                else -> R.drawable.style
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun displayCurrentDataTime() = with(binding) {
        val currentDateTime = LocalDateTime.now()
        val formatterDate = DateTimeFormatter.ofPattern("dd MMMM")
        val formatterTime = DateTimeFormatter.ofPattern("HH:mm")
        val formattedDate = currentDateTime.format(formatterTime)
        val formattedTime = currentDateTime.format(formatterTime)
        date.text = formattedDate
        time.text = formattedTime
    }
}