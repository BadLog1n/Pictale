package com.oneseed.pictale

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.firebase.database.DataSnapshot
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
            val inputCode = binding.codeInputText.text
            if (inputCode.toString().isNotEmpty()) {
                database.child(inputCode.toString()).get().addOnSuccessListener {
                    if (it.exists()) {
                        getAllData(inputCode.toString(), it)
                        inputCode.clear()
                        view.hideKeyboard()
                        view.findNavController().navigate(R.id.contentFragment)
                    } else Toast.makeText(requireContext(), "Код не найден", Toast.LENGTH_SHORT)
                        .show()
                }
            } else Toast.makeText(requireContext(), "Код не может быть пустым", Toast.LENGTH_SHORT)
                .show()

        }

    }


    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun getAllData(inputCode: String, it: DataSnapshot) {
        val sharedPref: SharedPreferences? = activity?.getSharedPreferences(
            getString(R.string.sharedPref), Context.MODE_PRIVATE
        )
        val editor: SharedPreferences.Editor? = sharedPref?.edit()
        val map: MutableMap<String, String> = HashMap()
        map[getString(R.string.titleValue)] = it.child("title").value.toString()
        map[getString(R.string.mainTextValue)] = it.child("mainText").value.toString()
        map[getString(R.string.emailTextValue)] = it.child("email").value.toString()
        map[getString(R.string.photoPathValue)] = it.child("photo").value.toString()
        map[getString(R.string.codeValue)] = inputCode
        map[getString(R.string.imageTitleValue)] = it.child("imageTitle").value.toString()
        map[getString(R.string.contactValue)] = it.child("contact").value.toString()
        for ((key, value) in map) {
            editor?.putString(key, value)
        }

        editor?.apply()

    }
}
