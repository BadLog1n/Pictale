package com.oneseed.pictale

import android.content.Context
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.oneseed.pictale.databinding.FragmentContentBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class ContentFragment : Fragment() {
    private lateinit var binding: FragmentContentBinding
    private var rcAdapter = ContentPhotoAdapter()


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

        val imageRc: RecyclerView = view.findViewById(R.id.imagesRcView)
        imageRc.adapter = rcAdapter
        rcAdapter.notifyItemChanged(rcAdapter.itemCount)
        rcAdapter.recordsList = ArrayList()
        rcAdapter.addPhotoRecord(
            PhotoRecord(
                "1.jpg"
            )
        )
        rcAdapter.addPhotoRecord(
            PhotoRecord(
                "1.jpg"
            )
        )
        rcAdapter.addPhotoRecord(
            PhotoRecord(
                "1.jpg"
            )
        )
        rcAdapter.addPhotoRecord(
            PhotoRecord(
                "1.jpg"
            )
        )
        rcAdapter.addPhotoRecord(
            PhotoRecord(
                "1.jpg"
            )
        )
        imageRc.adapter = rcAdapter
        rcAdapter.notifyItemChanged(rcAdapter.itemCount)
        imageRc.adapter = rcAdapter
        val linearLayoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, true)
        linearLayoutManager.stackFromEnd = true
        imageRc.layoutManager = linearLayoutManager
            Toast.makeText(requireContext(), rcAdapter.itemCount.toString(), Toast.LENGTH_SHORT).show()



        binding.backAction.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun displayValue() {
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


        CoroutineScope(Dispatchers.Main).launch {
            try {
                val imageRef = Firebase.storage.reference
                val maxDownloadSize = 5L * 1024 * 1024 * 1024
                val bytes = imageRef.child("1/1.jpg").getBytes(maxDownloadSize).await()
                val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                binding.mainImage.setImageBitmap(bmp)
                binding.toImgProgress.visibility = View.GONE
            } catch (_: Exception) {
            }
        }
    }

}