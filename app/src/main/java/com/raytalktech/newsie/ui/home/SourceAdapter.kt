package com.raytalktech.newsie.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.raytalktech.newsie.data.source.local.entity.DataEntity
import com.raytalktech.newsie.databinding.ItemCircleIconBinding

class SourceAdapter(private val onClick: (DataEntity) -> Unit) :
    ListAdapter<DataEntity, CircleIconViewHolder>(
        DIFF_CALLBACK
    ) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CircleIconViewHolder {
        return CircleIconViewHolder(
            ItemCircleIconBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CircleIconViewHolder, position: Int) {
        return holder.bind(getItem(position), onClick)
    }


    companion object {
        const val VIEW_TYPE = 8888

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataEntity>() {
            override fun areItemsTheSame(
                oldItem: DataEntity,
                newItem: DataEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: DataEntity,
                newItem: DataEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}