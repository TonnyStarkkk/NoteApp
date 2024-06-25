package com.example.noteapp.Interface

import com.example.noteapp.ui.data.models.NoteModel

interface OnClickItem {
    fun onLongClick(noteModel: NoteModel)

    fun onClick(noteModel: NoteModel)
}