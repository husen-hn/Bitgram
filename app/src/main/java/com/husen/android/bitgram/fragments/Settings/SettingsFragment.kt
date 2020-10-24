package com.husen.android.bitgram.fragments.Settings

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.husen.android.bitgram.R
import com.husen.android.bitgram.databinding.FragmentSettingsBinding

private const val TAG = "SettingsFragment"

class SettingsFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentSettingsBinding
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        binding.ivHomeS.setOnClickListener(this)
        binding.cvLastChanges.setOnClickListener(this)
        binding.cvShare.setOnClickListener(this)
        binding.cvAbout.setOnClickListener(this)
        binding.cvSuggestion.setOnClickListener(this)
        binding.cvFamiliarity.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0!!.id) {
            R.id.iv_home_s -> {
                navController.navigate(R.id.action_nav_settings_fragment_to_nav_main_fragment)
            }
            R.id.cv_last_changes -> {
                navController.navigate(R.id.action_nav_settings_fragment_to_lastChangesFragment)
            }
            R.id.cv_share -> {
                shareApp()
            }
            R.id.cv_about -> {
                navController.navigate(R.id.action_nav_settings_fragment_to_aboutFragment)
            }
            R.id.cv_suggestion -> {
                sendEmail()
            }
            R.id.cv_familiarity -> {
                navController.navigate(R.id.action_nav_settings_fragment_to_introFragmentOne)
            }
        }
    }
    private fun shareApp() {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        shareIntent.putExtra(
            Intent.EXTRA_TEXT,
            "https://cafebazaar.ir/app/com.husen.android.bitgram"
        )
        shareIntent.putExtra(
            Intent.EXTRA_SUBJECT,
            "بیتگرام : بروزترین قیمت رمزارزها و اطلاعات بازار"
        )
        startActivity(Intent.createChooser(shareIntent, "اشتراک"))
    }
    private fun sendEmail() {
        Log.e(TAG, "Send email")
        val TO = arrayOf("hosseinspell@gmail.com")
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.data = Uri.parse("mailto:")
        emailIntent.type = "text/plain"
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO)
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "بیتگرام: پیشنهاد و انتقاد")
        try {
            startActivity(Intent.createChooser(emailIntent, "ارسال ایمیل ..."))
            Log.e(TAG, "Finished sending email...")
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(
                context,
                "شما هیچ برنامه ای جهت ارسال ایمیل پیشنهاد و انتقاد ندارید.", Toast.LENGTH_LONG
            ).show()
        }
    }
}