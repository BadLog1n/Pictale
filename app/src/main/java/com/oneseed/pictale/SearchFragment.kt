package com.oneseed.pictale

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.oneseed.pictale.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = FirebaseDatabase.getInstance().getReference("key")
        binding.submit.setOnClickListener {
            val inputCode = binding.codeInputText.text.toString()
            if (inputCode.isNotEmpty()){
                database.child(inputCode).get().addOnSuccessListener {
                    if (it.exists()) view.findNavController().navigate(R.id.contentFragment)
                    else Toast.makeText(requireContext(), "Код не найден", Toast.LENGTH_SHORT).show()
                }
            }
            else Toast.makeText(requireContext(), "Код не может быть пустым", Toast.LENGTH_SHORT).show()

        }

    }
}
