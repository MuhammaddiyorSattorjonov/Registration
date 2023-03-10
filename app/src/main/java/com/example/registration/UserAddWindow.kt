package com.example.registration

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.registration.databinding.FragmentUserAddWindowBinding
import com.example.registration.db.MyDbHelper
import com.example.registration.models.MyShablon

class UserAddWindow : Fragment() {
    private var picturePath:String? = null
    private val binding by lazy { FragmentUserAddWindowBinding.inflate(layoutInflater) }
    lateinit var myDbHelper: MyDbHelper

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        var countries = arrayOf(
            "Afghanistan",
            "Albania",
            "Algeria",
            "Andorra",
            "Angola",
            "Antigua and Barbuda",
            "Argentina",
            "Armenia",
            "Australia",
            "Austria",
            "Azerbaijan",
            "Bahamas",
            "Bahrain",
            "Bangladesh",
            "Barbados",
            "Belarus",
            "Belgium",
            "Belize",
            "Benin",
            "Bhutan",
            "Bolivia",
            "Bosnia and Herzegovina",
            "Botswana",
            "Brazil",
            "Brunei",
            "Bulgaria",
            "Burkina Faso",
            "Burundi",
            "Cabo Verde",
            "Cambodia",
            "Cameroon",
            "Canada",
            "Central African Republic",
            "Chad",
            "Chile",
            "China",
            "Colombia",
            "Comoros",
            "Congo, Democratic Republic of the",
            "Congo, Republic of the",
            "Costa Rica",
            "Cote d'Ivoire",
            "Croatia",
            "Cuba",
            "Cyprus",
            "Czech Republic",
            "Denmark",
            "Djibouti",
            "Dominica",
            "Dominican Republic",
            "Ecuador",
            "Egypt",
            "El Salvador",
            "Equatorial Guinea",
            "Eritrea",
        )
        myDbHelper = MyDbHelper(requireContext())
        var countryPos = 0
        var countryState = false
        binding.spinnerTime.setAdapter(ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            countries))
        binding.spinnerTime.setOnItemClickListener { parent, view, position, id ->
            countryPos = position
            countryState = true
        }
        binding.image.setOnClickListener {
            choosePhotoFromGallary()
        }

        binding.apply {
            btnSave.setOnClickListener {
                val name = name.text.toString().trim()
                val number = number.text.toString().trim()
                val manzil = manzil.text.toString().trim()
                val password = parol.text.toString()

                if (name.isNotEmpty() && number.isNotEmpty() && manzil.isNotEmpty() && countryState && password.isNotEmpty()&& picturePath!=null ) {
                    if (binding.number.text.length == 9){
                        val myShablon = MyShablon(
                            name,
                            number,
                            countryPos,
                            manzil,
                            password,
                            picturePath
                        )
                        myDbHelper.addUser(myShablon)

                        binding.name.text.clear()
                        binding.number.text.clear()
                        binding.manzil.text.clear()
                        binding.parol.text.clear()
                        countryPos = 0
                        countryState = false
                        findNavController().navigate(R.id.usersList)
                    }else{
                        binding.layoutNumber.error = "Raqamingizni oxirigacha to'ldiring"
                    }
                } else {
                    Toast.makeText(context,
                        "Ma'lumotlaringizni oxirigacha to'ldiring",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }

        return binding.root
    }
    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(requireContext())
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf("Select photo from gallery")
        pictureDialog.setItems(
            pictureDialogItems
        ) { _, which ->
            when (which) {
                0 -> choosePhotoFromGallary()
            }
        }
        pictureDialog.show()
    }

    private fun choosePhotoFromGallary() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, 1)
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun takePhotoFromCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireContext().packageManager)?.also {
                startActivityForResult(takePictureIntent, 100)
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            val selectedImage = data.data
            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = requireContext().contentResolver.query(
                selectedImage!!,
                filePathColumn,
                null,
                null,
                null
            )
            cursor!!.moveToFirst()
            val columnIndex = cursor.getColumnIndex(filePathColumn[0])
            picturePath = cursor.getString(columnIndex)
            cursor.close()
            binding.image.setImageBitmap(BitmapFactory.decodeFile(picturePath))
            Toast.makeText(requireContext(), picturePath, Toast.LENGTH_SHORT).show()
        }
    }
}