package com.example.registration

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.registration.databinding.FragmentBottomSheetDialogBinding
import com.example.registration.databinding.FragmentUsersListBinding
import com.example.registration.db.MyDbHelper
import com.example.registration.models.MyShablon
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class UsersList : Fragment(),RvClick {
    private val binding by lazy { FragmentUsersListBinding.inflate(layoutInflater) }
    lateinit var myDbHelper: MyDbHelper
    lateinit var rvAdapter: RvAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        myDbHelper = MyDbHelper(requireContext())
        rvAdapter = RvAdapter(myDbHelper.getAllUser(),this)
        binding.rv.adapter = rvAdapter

        binding.back.setOnClickListener {
            findNavController().navigate(R.id.registrWindow)
        }


        return binding.root

    }



    override fun backClick(myShablon: MyShablon, position: Int) {
        val blank = FragmentBottomSheetDialogBinding.inflate(layoutInflater)
       val dialog = com.google.android.material.bottomsheet.BottomSheetDialog(requireContext())
        dialog.setContentView(blank.root)
        dialog.show()
        blank.name.text = myShablon.name
        blank.manzil.text = myShablon.number
        blank.imageProfile.setImageBitmap(BitmapFactory.decodeFile(myShablon.image))

        blank.call.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+myShablon.number))
            startActivity(intent)
        }
        blank.sms.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("smsto: " + myShablon.number)
            startActivity(intent)
        }
    }

}