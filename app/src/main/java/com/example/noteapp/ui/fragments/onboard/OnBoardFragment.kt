package com.example.noteapp.ui.fragments.onboard

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.airbnb.lottie.Lottie
import com.airbnb.lottie.LottieConfig
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentOnBoardBinding
import com.example.noteapp.ui.adapters.OnBoardViewPagerAdapter

class OnBoardFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupListener()
    }

    private fun initialize() {
        binding.viewpager2.adapter = OnBoardViewPagerAdapter(this@OnBoardFragment)
    }

    private fun setupListener() = with(binding.viewpager2) {
        registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 2){
                    binding.tvSkip.visibility = View.INVISIBLE
                }else{
                    binding.tvSkip.visibility = View.VISIBLE
                    binding.tvSkip.setOnClickListener{
                        setCurrentItem(currentItem + 2, true)
                    }
                }
                if (position == 2){
                    binding.tvStart.visibility = View.VISIBLE
                }else{
                    binding.tvStart.visibility = View.INVISIBLE
                    binding.tvStart.setOnClickListener{
                        findNavController().navigate(R.id.noteFragment)
                    }
                }
            }
        })
    }
}