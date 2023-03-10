package com.example.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.registration.databinding.FragmentRegistrWindowBinding
import com.example.registration.db.MyDbHelper
import com.example.registration.models.MyShablon

class RegistrWindow : Fragment() {
    private val binding by lazy { FragmentRegistrWindowBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding.registr.setOnClickListener {
            findNavController().navigate(R.id.userAddWindow)
        }

        var list = MyDbHelper(requireContext()).getAllUser() as ArrayList<MyShablon>
        binding.tizimgaKirish.setOnClickListener {
            if (binding.code.text.isNotEmpty() && binding.number.text.isNotEmpty()) {
                if (binding.number.text.length==9){
                    for (i in list) {
                        if (i.number == binding.number.text.toString() && i.password == binding.code.text.toString()) {
                            findNavController().navigate(R.id.usersList)
                            binding.apply {
                                number.text.clear()
                                code.text.clear()
                            }
                        }
                    }
                }else{
                    binding.numberLay7out.error = "Raqamingizni oxirigacha to'ldiring"
                }

            } else {
                Toast.makeText(requireContext(), "Ma'lumotlaringizni kiriting", Toast.LENGTH_SHORT)
                    .show()
            }
        }


        return binding.root
    }
}