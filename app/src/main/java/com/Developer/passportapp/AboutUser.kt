package com.Developer.passportapp

import Entity.User
import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.Developer.passportapp.databinding.FragmentAboutUserBinding


class AboutUser : Fragment() {
    lateinit var binding: FragmentAboutUserBinding

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutUserBinding.inflate(layoutInflater)

        val user = arguments?.getSerializable("user") as User

        binding.imageUsers.setImageURI(Uri.parse(user.imageUser.toString()))
        binding.usersNameFather.text = "${user.name}  ${user.fatherName} o'g'li"
        binding.aboutUsers.text =
            "Otasining ismi :${user.fatherName}\n Yashash joyi :${user.countryName} ${user.cityName}\n" +
                    "yashash uy : ${user.addressHouse}\n Jinsi : ${user.M_or_F}\n Pasport olgan vaqti : ${user.dateReceipt}\n Pasport muddati : ${user.dateTerm} \n Pasport Seria Number: ${user.seriaNumber}"

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }
}