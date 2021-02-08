package com.example.roomdatabasepractice

import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.drawToBitmap
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.roomdatabasepractice.data.User
import com.example.roomdatabasepractice.data.UserViewModel
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
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
        view.profile_pic.setImageResource(R.drawable.images)
        view.profile_pic.setOnClickListener{
            pickImage()
        }

        return view
    }

    private fun insertDataToDatabase()
    {
        val firstName = updateinputText1.text.toString()
        val lastName = updateinputText2.text.toString()
        val age = updateinputText3.text.toString()

        if(inputCheck(firstName, lastName, age))
        {
            val pp = profile_pic.drawToBitmap(Bitmap.Config.ARGB_8888)
            val user = User(0, firstName, lastName, Integer.parseInt(age), pp)
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

    private fun pickImage()
    {
        context?.let {
            CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .setFixAspectRatio(true)
                .setRequestedSize(100, 100, CropImageView.RequestSizeOptions.RESIZE_EXACT)
                .start(it, this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == AppCompatActivity.RESULT_OK) {
                val resultUri = result.uri
                profile_pic.setImageURI(resultUri)
                //val bitmap = imageView.drawToBitmap(Bitmap.Config.ARGB_8888)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
                Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

}