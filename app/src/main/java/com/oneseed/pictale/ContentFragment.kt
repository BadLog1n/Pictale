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
    private lateinit var code: String
    private lateinit var photoPath: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentContentBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayValue()

        val imageRc: RecyclerView = view.findViewById(R.id.imagesRcView)
        imageRc.adapter = rcAdapter
        rcAdapter.notifyItemChanged(rcAdapter.itemCount)
        rcAdapter.recordsList = ArrayList()
        rcAdapter.addPhotoRecord(
            PhotoRecord(
                "$code.jpg"
            )
        )
        rcAdapter.addPhotoRecord(
            PhotoRecord(
                "$code.jpg"
            )
        )
        rcAdapter.addPhotoRecord(
            PhotoRecord(
                "$code.jpg"
            )
        )
        rcAdapter.addPhotoRecord(
            PhotoRecord(
                "$code.jpg"
            )
        )
        rcAdapter.addPhotoRecord(
            PhotoRecord(
                "$code.jpg"
            )
        )
        imageRc.adapter = rcAdapter
        rcAdapter.notifyItemChanged(rcAdapter.itemCount)
        imageRc.adapter = rcAdapter
        val linearLayoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, true)
        linearLayoutManager.stackFromEnd = true
        imageRc.layoutManager = linearLayoutManager


        binding.backAction.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun displayValue() {
        val sharedPref: SharedPreferences? = activity?.getSharedPreferences(
            getString(R.string.sharedPref), Context.MODE_PRIVATE
        )
        val allEntries: Map<String, *> = sharedPref?.all ?: return

        for ((key, value) in allEntries) {
            when (key) {
                getString(R.string.codeValue) -> code = value.toString()
                getString(R.string.photoPathValue) -> photoPath = value.toString()
                getString(R.string.titleValue) -> binding.titleText.text = value.toString()
                getString(R.string.mainTextValue) -> binding.mainText.text = value.toString()
                getString(R.string.emailTextValue) -> binding.emailContactText.text = value.toString()
            }
        }
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val imageRef = Firebase.storage.reference
                val maxDownloadSize = 5L * 1024 * 1024 * 1024
                val bytes = imageRef.child("$code/1.jpg").getBytes(maxDownloadSize).await()
                val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                binding.mainImage.setImageBitmap(bmp)
                binding.toImgProgress.visibility = View.GONE
            } catch (_: Exception) {
            }
        }
    }

}