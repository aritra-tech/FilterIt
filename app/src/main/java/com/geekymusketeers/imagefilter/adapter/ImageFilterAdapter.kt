package com.geekymusketeers.imagefilter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geekymusketeers.imagefilter.data.ImageFilter
import com.geekymusketeers.imagefilter.databinding.ActivityEditImageScreenBinding
import com.geekymusketeers.imagefilter.databinding.ItemContainerFilterBinding

class ImageFilterAdapter(private val imageFilter: List<ImageFilter>) :
    RecyclerView.Adapter<ImageFilterAdapter.ImageFilterViewHolder>(){

    inner class ImageFilterViewHolder(val binding: ItemContainerFilterBinding) :
            RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageFilterViewHolder {
        val binding = ItemContainerFilterBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        )
        return ImageFilterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageFilterViewHolder, position: Int) {
        with(holder){
            with(imageFilter[position]){
                binding.imageFilterPreview.setImageBitmap(filterPreview)
                binding.textFilterName.text = name
            }
        }
    }

    override fun getItemCount() = imageFilter.size
}