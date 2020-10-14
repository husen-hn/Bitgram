package com.husen.android.bitgram.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.husen.android.bitgram.databinding.FragmentLastChangesBinding

class LastChangesFragment : Fragment() {

    private lateinit var binding: FragmentLastChangesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLastChangesBinding.inflate(inflater, container, false)
        return binding.root
    }
}