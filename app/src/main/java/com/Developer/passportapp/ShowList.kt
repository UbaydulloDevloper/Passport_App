package com.Developer.passportapp

import Adapters.MyAdapters
import DataBase.AppDatabase
import Entity.User
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.Developer.passportapp.databinding.FragmentShowListBinding

class ShowList : Fragment() {
    lateinit var binding: FragmentShowListBinding
    lateinit var appDatabase: AppDatabase
    lateinit var list: ArrayList<User>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShowListBinding.inflate(layoutInflater)
        list = ArrayList()


        binding.sorting.setOnClickListener {
            Toast.makeText(binding.root.context, "Sorting click", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        list = ArrayList()
        appDatabase = AppDatabase.getInstance(binding.root.context)
        list.addAll(appDatabase.userDao().getAllUser())
         binding.recycleViewUsers.adapter = MyAdapters(list, object : MyAdapters.Click {
             override fun itemClick(user: User, position: Int) {
                 findNavController().navigate(R.id.aboutUser, bundleOf("user" to user))
             }
             override fun itemdelete(user: User, position: Int) {
                 appDatabase.userDao().deleteUser(user)
                 Toast.makeText(binding.root.context, "delete", Toast.LENGTH_SHORT).show()
                 onResume()
             }
         })

    }


}