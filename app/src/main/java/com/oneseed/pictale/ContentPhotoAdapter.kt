package com.oneseed.pictale

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.oneseed.pictale.databinding.ImgItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ContentPhotoAdapter : RecyclerView.Adapter<ContentPhotoAdapter.PhotoHolder>() {

    var recordsList = ArrayList<PhotoRecord>()


    class PhotoHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = ImgItemBinding.bind(item)

        fun bind(photoRecord: PhotoRecord) {
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val imageRef = Firebase.storage.reference
                    val maxDownloadSize = 5L * 1024 * 1024 * 1024
                    val bytes =
                        imageRef.child("1/${photoRecord.uri}").getBytes(maxDownloadSize).await()
                    val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    binding.image.setImageBitmap(bmp)
                    binding.imgProgress.visibility = View.GONE
                } catch (_: Exception) {
                }
            }
        }

    }

    fun addPhotoRecord(feedRecord: PhotoRecord) {
        recordsList.add(feedRecord)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.img_item, parent, false)

        return PhotoHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        holder.bind(recordsList[position])
    }

    override fun getItemCount(): Int {
        return recordsList.size
    }
}

data class PhotoRecord(
    val uri: String
)
