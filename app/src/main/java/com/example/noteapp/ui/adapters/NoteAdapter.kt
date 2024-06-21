package com.example.noteapp.ui.adapters

import android.content.ClipData.Item
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.databinding.ItemNoteBinding
import com.example.noteapp.ui.data.models.NoteModel
import com.example.noteapp.R

class NoteAdapter:
        androidx.recyclerview.widget.ListAdapter<NoteModel, NoteAdapter.ViewHolder>(DiffCallback()) {
    class ViewHolder(private val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NoteModel) {
            binding.itemTitle.text = item.title
            binding.itemDescription.text = item.description
            binding.itemTime.text = item.time
            binding.itemDate.text = item.date

            val context = binding.root.context

            val drawable = ContextCompat.getDrawable(context, item.color.toInt())
            binding.itemNote.background = drawable
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<NoteModel>(){
        override fun areItemsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
            return oldItem.title == newItem.title
        }
    }
}