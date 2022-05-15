package com.geekymusketeers.imagefilter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.geekymusketeers.imagefilter.R
import com.geekymusketeers.imagefilter.data.ImageFilter
import com.geekymusketeers.imagefilter.databinding.ActivityEditImageScreenBinding
import com.geekymusketeers.imagefilter.databinding.ItemContainerFilterBinding
import com.geekymusketeers.imagefilter.listener.ImageFilterListener

class ImageFilterAdapter(
    private val imageFilter: List<ImageFilter>,
    private val imageFilterListener: ImageFilterListener
    ) : RecyclerView.Adapter<ImageFilterAdapter.ImageFilterViewHolder>(){

    private var selectedFilteredPosition = 0
    private var previouslySelectedPosition = 0

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
                binding.root.setOnClickListener{
                    if (position!=selectedFilteredPosition){
                        imageFilterListener.onFilterSelected(this)
                        previouslySelectedPosition = selectedFilteredPosition
                        selectedFilteredPosition = position
                        with(this@ImageFilterAdapter){
                            notifyItemChanged(previouslySelectedPosition, Unit)
                            notifyItemChanged(selectedFilteredPosition,Unit)
                        }
                    }
                }
            }
            binding.textFilterName.setTextColor(
                ContextCompat.getColor(
                    binding.textFilterName.context,
                    if (selectedFilteredPosition == position){
                        R.color.primaryDark
                    }else{
                        R.color.primaryText
                    }
                )
            )
        }
    }

    override fun getItemCount() = imageFilter.size
}