package com.miu.gardenmate.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.miu.gardenmate.R
import com.miu.gardenmate.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentHomeBinding.inflate(inflater, container,false)

        binding.cardImageView.setOnClickListener {
            val itemAction = HomeFragmentDirections.actionHomeFragmentToGardenLogFragment()
            findNavController().navigate(itemAction)
        }

        return binding.root
    }

}