package com.husen.android.bitgram.fragments.OnBording

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.husen.android.bitgram.R
import kotlinx.android.synthetic.main.fragment_intro_three.*

class IntroFragmentThree : Fragment(), View.OnClickListener {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intro_three, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        btn_three_next.setOnClickListener(this)
        btn_three_back.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0!!.id) {
            R.id.btn_three_next -> {
                navController.navigate(R.id.action_introFragmentThree_to_introFragmentFour)
            }
            R.id.btn_three_back -> {
                navController.navigate(R.id.action_introFragmentThree_to_introFragmentTwo)
            }
        }
    }

}