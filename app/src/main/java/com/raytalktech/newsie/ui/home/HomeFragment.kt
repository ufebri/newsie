package com.raytalktech.newsie.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.opensooq.pluto.PlutoView
import com.opensooq.pluto.base.PlutoAdapter
import com.opensooq.pluto.base.PlutoViewHolder
import com.opensooq.pluto.listeners.OnItemClickListener
import com.raytalktech.newsie.R
import com.raytalktech.newsie.data.source.local.entity.DataEntity
import com.raytalktech.newsie.databinding.ContentHomeFragmentBinding
import com.raytalktech.newsie.ui.detail.DetailActivity
import com.raytalktech.newsie.ui.detail.DetailActivity.Companion.DATA_RESULT
import com.raytalktech.newsie.utils.RecyclerDecoration
import com.raytalktech.newsie.utils.ViewModelFactory
import com.raytalktech.newsie.utils.vo.Status

class HomeFragment : Fragment() {

    companion object {
        val ITEM_PER_ADD = 10
        val BANNER_AD_ID = "ca-app-pub-3940256099942544/6300978111"
    }

    private var _contentBinding: ContentHomeFragmentBinding? = null
    private val binding get() = _contentBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _contentBinding = ContentHomeFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            MobileAds.initialize(requireActivity()) { }

            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

            newsAdapter = NewsAdapter()

            populateDataNews()
            viewModel.breakingNews().observe(viewLifecycleOwner, breakingNewsObserver)
        }
    }

    private fun populateDataNews() {

        val category = resources.getStringArray(R.array.category_news)

        for (categories in category.indices) {
            viewModel.getAllNewsData(category[categories]).observe(viewLifecycleOwner, { result ->
                when (result.status) {
                    Status.LOADING -> {}
                    Status.SUCCESS -> {
                        if (result.data != null) {
                            newsAdapter.submitData(result.data)
                            setupAds(result.data.size)
                            if (category[categories].lastIndex == category.size) showViewDataNews()
                            newsAdapter.notifyDataSetChanged()
                        }
                    }
                    Status.ERROR -> {
                        Toast.makeText(
                            context,
                            "BreakingNews: Failed to Get Data",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                }
            })
        }
    }

    private fun setupAds(size: Int) {
        for (i in 1..size step ITEM_PER_ADD) {
            val adView = AdView(requireActivity())
            adView.adSize = AdSize.BANNER
            adView.adUnitId = BANNER_AD_ID
        }
    }

    private fun showViewDataNews() {
        binding?.let {
            with(it.rvListNews) {
                layoutManager =
                    GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                addItemDecoration(RecyclerDecoration(10, true))
                setHasFixedSize(true)
                adapter = newsAdapter
            }
        }
    }

    private val breakingNewsObserver =
        Observer<List<DataEntity>> { data -> if (data != null) showViewBreakingNews(data) }

    private fun showViewBreakingNews(data: List<DataEntity>) {
        val sliderAdapter = FeaturedImageAdapter(
            data,
            object : OnItemClickListener<DataEntity> {
                override fun onItemClicked(item: DataEntity?, position: Int) {
                    startActivity(
                        Intent(context, DetailActivity::class.java).putExtra(
                            DATA_RESULT, item
                        )
                    )
                }
            })

        binding?.let {
            with(it.ivSlideTopNews) {
                create(sliderAdapter, 15000, lifecycle)
                setIndicatorPosition(PlutoView.IndicatorPosition.RIGHT_BOTTOM)
            }
        }
    }

    class FeaturedImageAdapter(
        items: List<DataEntity>,
        onItemClickListener: OnItemClickListener<DataEntity>? = null
    ) : PlutoAdapter<DataEntity, FeaturedImageAdapter.ViewHolder>(
        items as MutableList<DataEntity>,
        onItemClickListener
    ) {

        override fun getViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(parent, R.layout.item_featured_news)
        }

        class ViewHolder(parent: ViewGroup, itemLayoutId: Int) :
            PlutoViewHolder<DataEntity>(parent, itemLayoutId) {
            private var ivPoster: ImageView = getView(R.id.iv_itemFeatureCover)
            private var tvTitle: TextView = getView(R.id.tv_itemFeatureTitle)
            private var tvCategory: TextView = getView(R.id.tv_itemFeatureCategory)

            override fun set(item: DataEntity, position: Int) {
                Glide.with(itemView.context).load(item.urlToImage).into(ivPoster)
                tvTitle.text = item.title
                tvCategory.text = item.category
            }
        }
    }

}