package com.raytalktech.newsie.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.material.imageview.ShapeableImageView
import com.raytalktech.newsie.R
import com.raytalktech.newsie.data.source.local.entity.DataEntity

class NewsAdapter(private val onClick: (DataEntity) -> Unit) :
    ListAdapter<DataEntity, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    private val ITEM: Int = 1
    private val BANNER: Int = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM -> {
                val itemLayout: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_primary_news, parent, false)
                DataViewHolder(itemLayout)
            }

            BANNER -> {
                val view =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_list_admob, parent, false)
                return AdViewHolder(view)
            }

            else -> {
                val itemLayout: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_primary_news, parent, false)
                DataViewHolder(itemLayout)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            ITEM -> {
                val holders = holder as DataViewHolder
                holders.bind(getItem(position), onClick)
            }

            BANNER -> {}
        }
    }

    class DataViewHolder(binding: View) :
        RecyclerView.ViewHolder(binding) {

        var ivCover: ShapeableImageView =
            binding.findViewById(R.id.iv_itemPrimaryImage) as ShapeableImageView
        private var tvItemPrimaryTitle: TextView =
            binding.findViewById(R.id.tv_itemPrimaryTitle) as TextView
        private var tvItemPrimaryAuthor: TextView =
            binding.findViewById(R.id.tv_itemPrimaryAuthor) as TextView
        private var tvItemPrimaryTime: TextView =
            binding.findViewById(R.id.tv_itemPrimaryTime) as TextView

        fun bind(mData: DataEntity, onClick: (DataEntity) -> Unit) {

            //setData
            Glide.with(itemView.context)
                .load(mData.urlToImage ?: R.drawable.baseline_broken_image_24)
                .override(ivCover.width, ivCover.height)
                .into(ivCover)

            tvItemPrimaryTitle.text = mData.title.replaceAfter("- ", "")
            tvItemPrimaryTime.text = mData.publishedAt
            tvItemPrimaryAuthor.text = String.format("By ${mData.author}")

            itemView.setOnClickListener { onClick(mData) }
        }

    }

    inner class AdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            val adViewA: AdView = itemView.findViewById<View>(R.id.adViewA) as AdView
            val adRequest = AdRequest.Builder().build()
            adViewA.loadAd(adRequest)
        }
    }

//    override fun getItemViewType(position: Int): Int {
//        return listData[position].viewType
//
//    }
//
//    private fun getItem(position: Int): DataEntity {
//        return listData[position]
//    }

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