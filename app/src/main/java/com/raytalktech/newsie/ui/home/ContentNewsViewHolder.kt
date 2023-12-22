package com.raytalktech.newsie.ui.home

import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.raytalktech.newsie.R
import com.raytalktech.newsie.data.source.local.entity.DataEntity
import com.raytalktech.newsie.databinding.ItemContentNewsBinding
import com.raytalktech.newsie.utils.DataHelper

class ContentNewsViewHolder(private val binding: ItemContentNewsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(mData: DataEntity, onClick: (DataEntity) -> Unit) {
        binding.let {
            Glide.with(it.root).load(mData.urlToImage).placeholder(
                AppCompatResources.getDrawable(
                    it.root.context,
                    R.drawable.baseline_broken_image_24
                )
            ).error(
                AppCompatResources.getDrawable(
                    it.root.context,
                    R.drawable.loading_animation
                )
            )
                .into(it.sivContent)

            it.tvItemFeatureTitle.text = mData.title
            it.tvItemDateFeatureCover.text =
                String.format("%s • %s", mData.name, DataHelper.formatDateString(mData.publishedAt))

            Glide.with(it.root.context)
                .load(mData.faviconUrl)
                .placeholder(
                    AppCompatResources.getDrawable(
                        it.root.context,
                        R.drawable.loading_animation
                    )
                ).circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .error(
                    AppCompatResources.getDrawable(
                        it.root.context,
                        R.drawable.baseline_broken_image_24
                    )
                )
                .into(it.sivFavicon)
        }

        itemView.setOnClickListener { onClick(mData) }
    }
}