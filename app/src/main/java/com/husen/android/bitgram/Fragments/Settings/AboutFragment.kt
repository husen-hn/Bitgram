package com.husen.android.bitgram.Fragments.Settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.husen.android.bitgram.databinding.FragmentAboutBinding
import kotlinx.android.synthetic.main.fragment_about.*

class AboutFragment : Fragment() {

    private lateinit var binding: FragmentAboutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.btnKucoin.setOnClickListener {
            openUrl("https://www.kucoin.com/")
        }
        binding.btnRamzinex.setOnClickListener {
            openUrl("https://ramzinex.com/")
        }
        binding.btnGithub.setOnClickListener {
            openUrl("https://github.com/husen-hn/Bitgram")
        }
    }
    private fun openUrl(url: String) {
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }
}