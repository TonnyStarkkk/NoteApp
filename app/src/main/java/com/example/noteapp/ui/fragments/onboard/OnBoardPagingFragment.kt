package com.example.noteapp.ui.fragments.onboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentOnBoardPagingBinding

class OnBoardPagingFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardPagingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoardPagingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() = with(binding) {
        when(requireArguments().getInt(ARG_ONBOARD_POSITION)){
            0 -> {
                onTv .text = "Очень удобный функционал"
                lottie.setAnimation(R.raw.on_board1)
            }
            1 -> {
                onTv.text = "Быстрый, качественный продукт"
                lottie.setAnimation(R.raw.on_board2)
            }
            2 -> {
                onTv.text = "Куча функций и интересных фишек"
                lottie.setAnimation(R.raw.on_board3)
            }
        }
    }

    companion object{
        const val ARG_ONBOARD_POSITION = "onBoard"
    }
}