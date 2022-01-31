package com.raytalktech.newsie.ui.home

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.material.imageview.ShapeableImageView
import com.raytalktech.newsie.R
import com.raytalktech.newsie.data.source.local.entity.DataEntity
import com.raytalktech.newsie.databinding.ItemListAdmobBinding
import com.raytalktech.newsie.databinding.ItemPrimaryNewsBinding
import com.raytalktech.newsie.ui.detail.DetailActivity
import com.raytalktech.newsie.ui.detail.DetailActivity.Companion.DATA_RESULT

class NewsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val ITEM: Int = 1
    private val BANNER: Int = 2

    private var listData = ArrayList<DataEntity>()

    fun submitData(data: List<DataEntity>?) {
        if (data == null) return
        this.listData.clear()
        this.listData.addAll(data)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM -> {
                val itemLayout: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_primary_news, parent, false)
                DataViewHolder(itemLayout)
            }
            BANNER -> {
                val itemListAdmobBinding =
                    ItemListAdmobBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                AdViewHolder(itemListAdmobBinding)
            }
            else -> {
                val itemLayout: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_primary_news, parent, false)
                DataViewHolder(itemLayout)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ITEM -> {
                val holders = holder as DataViewHolder
                holders.bind(getItem(position))
            }
            BANNER -> {
                val bannerViewHolder = holder as AdViewHolder
                val
            }
        }
    }

    override fun getItemCount(): Int = listData.size

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

        fun bind(mData: DataEntity) {

            //setData
            Glide.with(itemView.context)
                .load(mData.urlToImage)
                .into(ivCover)

            tvItemPrimaryTitle.text = mData.title.replaceAfter("- ", "")
            tvItemPrimaryTime.text = mData.publishedAt
            tvItemPrimaryAuthor.text = String.format("By ${mData.author}")

            itemView.setOnClickListener {
                itemView.context.startActivity(
                    Intent(
                        itemView.context,
                        DetailActivity::class.java
                    ).putExtra(DATA_RESULT, mData)
                )
            }
        }

    }

    class AdViewHolder(itemView: ItemListAdmobBinding) : RecyclerView.ViewHolder(itemView.root) {
        init {
            itemView.adView.apply {
                isGone = true
                loadAd(AdRequest.Builder().build())
                adListener = object : AdListener() {
                    override fun onAdLoaded() {
                        super.onAdLoaded()
                        isVisible = true
                    }

                    override fun onAdFailedToLoad(errorCode: LoadAdError) {
                        Log.e("error", "onAdFailedToLoad: $errorCode")
                    }
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position % 10 == 0) {
            true -> BANNER
            else -> ITEM
        }

    }

    private fun getItem(position: Int): DataEntity {
        return listData[position]
    }
}