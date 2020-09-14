package com.husen.android.bitgram.NavigationDrawerItems

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.husen.android.bitgram.BitGramFragment
import com.husen.android.bitgram.R
import kotlinx.android.synthetic.main.fragment_suggestion.*

class SuggestionFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        button_id.setOnClickListener {
//                val emailsend: String = editText1.getText().toString()
//                val emailsubject: String = editText2.getText().toString()
//                val emailbody: String = editText3.getText().toString()
//
//                // define Intent object
//                // with action attribute as ACTION_SEND
//                val intent = Intent(Intent.ACTION_SEND)
//
//                // add three fiels to intent using putExtra function
//                intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailsend))
//                intent.putExtra(Intent.EXTRA_SUBJECT, emailsubject)
//                intent.putExtra(Intent.EXTRA_TEXT, emailbody)
//
//                // set type of intent
//                intent.type = "message/rfc822"
//
//                // startActivity with intent with chooser
//                // as Email client using createChooser function
//                startActivity(
//                    Intent
//                        .createChooser(
//                            intent,
//                            "Choose an Email client :"
//                        )
//                )
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_suggestion, container, false)
        return view
    }

    companion object {
        fun newInstance() = BitGramFragment()
    }

}