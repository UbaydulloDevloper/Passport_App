package com.Developer.passportapp

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.Developer.passportapp.databinding.FragmentHomeSplashBinding
import kotlinx.android.synthetic.main.fragment_home_splash.*

class HomeSplash : Fragment() {

    lateinit var binding: FragmentHomeSplashBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeSplashBinding.inflate(layoutInflater)
        val c = Runnable {
            splash_text.visibility = View.GONE
        }
        val handler = Handler()
        handler.postDelayed(c, 2000)

        binding.addUsersBtn.setOnClickListener {
            findNavController().navigate(R.id.addPassport)
        }

        binding.listShowBtn.setOnClickListener {
            findNavController().navigate(R.id.showList)
        }

        return binding.root
    }


}