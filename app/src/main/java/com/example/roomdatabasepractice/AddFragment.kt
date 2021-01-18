package com.example.roomdatabasepractice

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomdatabasepractice.data.User
import com.example.roomdatabasepractice.data.UserViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*

class AddFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_add, container, false)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.addButton.setOnClickListener {
            insertDataToDatabase()
        }

        return view
    }

    private fun insertDataToDatabase()
    {
        val firstName = inputText1.text.toString()
        val lastName = inputText2.text.toString()
        val age = inputText.text.toString()

        if(inputCheck(firstName, lastName, age))
        {
            val user = User(0, firstName, lastName, Integer.parseInt(age))
            userViewModel.addUser(user)
            findNavController().navigate(R.id.action_add_to_list)
            Toast.makeText(requireContext(), "Data Added!!", Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(requireContext(), "Field Can't be empty!!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(firstname:String, lastname:String, age: String) : Boolean
    {
        return !(TextUtils.isEmpty(firstname) && TextUtils.isEmpty(lastname) && TextUtils.isEmpty(age))
    }

}