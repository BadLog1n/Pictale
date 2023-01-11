package com.oneseed.pictale

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.oneseed.pictale.databinding.FragmentContentBinding


class ContentFragment : Fragment() {
    private lateinit var binding: FragmentContentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentContentBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        displayValue()

        super.onViewCreated(view, savedInstanceState)

        binding.backAction.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun displayValue(){
        val sharedPref: SharedPreferences? = activity?.getSharedPreferences(
            getString(R.string.sharedPref), Context.MODE_PRIVATE
        )
        sharedPref?.getString(getString(R.string.titleValue), null)?.let {
            binding.titleText.text = it
        }
        sharedPref?.getString(getString(R.string.mainTextValue), null)?.let {
            binding.mainText.text = it
        }
        sharedPref?.getString(getString(R.string.emailTextValue), null)?.let {
            binding.emailContactText.text = it
        }
    }

}