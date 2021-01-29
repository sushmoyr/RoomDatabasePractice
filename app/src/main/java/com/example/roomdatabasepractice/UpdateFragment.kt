package com.example.roomdatabasepractice

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomdatabasepractice.data.User
import com.example.roomdatabasepractice.data.UserViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.updateinputText1.setText(args.currentUser.firstName)
        view.updateinputText2.setText(args.currentUser.lastName)
        view.updateinputText3.setText(args.currentUser.age.toString())

        view.updateButton.setOnClickListener {
            updateItem()
        }

        setHasOptionsMenu(true)


        return view
    }

    private fun updateItem() {
        val firstName = updateinputText1.text.toString()
        val lastName = updateinputText2.text.toString()
        val age = updateinputText3.text.toString()
        if (inputCheck(firstName, lastName, age)) {
            val updatedUser = User(args.currentUser.id, firstName, lastName, Integer.parseInt(age))
            userViewModel.updateUser(updatedUser)
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            Toast.makeText(requireContext(), "Updated Successfully", Toast.LENGTH_SHORT).show()
        } else
            Toast.makeText(requireContext(), "Fill all fields", Toast.LENGTH_SHORT).show()
    }

    private fun inputCheck(firstname: String, lastname: String, age: String): Boolean {
        return !(TextUtils.isEmpty(firstname) && TextUtils.isEmpty(lastname) && TextUtils.isEmpty(
            age
        ))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.menu_delete) {
            val builder = MaterialAlertDialogBuilder(requireContext())
            builder.setPositiveButton("Yes") { _, _ ->
                userViewModel.deleteUser(args.currentUser)
                Toast.makeText(requireContext(), "Successfully Removed", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            }
            builder.setNegativeButton("No") { _, _ ->

            }
            builder.setTitle("Delete ${args.currentUser.firstName}")
            builder.setMessage("Are you sure want to delete ${args.currentUser.firstName} ${args.currentUser.lastName}?")
            builder.create().show()
        }
        return super.onOptionsItemSelected(item)
    }
}