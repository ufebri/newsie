package com.raytalktech.newsie.ui.home

import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.raytalktech.newsie.R
import com.raytalktech.newsie.data.source.local.entity.DataEntity
import com.raytalktech.newsie.databinding.ItemCircleIconBinding

class CircleIconViewHolder(private val binding: ItemCircleIconBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(mData: DataEntity, onClick: (DataEntity) -> Unit) {
        with(binding) {

            Glide.with(root.context)
                .load(mData.faviconUrl)
                .placeholder(
                    AppCompatResources.getDrawable(
                        binding.root.context,
                        R.drawable.loading_animation
                    )
                ).circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .error(
                    AppCompatResources.getDrawable(
                        binding.root.context,
                        R.drawable.baseline_broken_image_24
                    )
                )
                .into(sivIcon)
        }

        binding.root.setOnClickListener { onClick(mData) }
    }

}