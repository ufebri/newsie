package com.raytalktech.newsie.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.facebook.shimmer.ShimmerFrameLayout
import com.raytalktech.newsie.R
import com.raytalktech.newsie.data.source.local.entity.DataEntity
import com.raytalktech.newsie.databinding.ContentHomeFragmentBinding
import com.raytalktech.newsie.ui.detail.DetailActivity
import com.raytalktech.newsie.ui.detail.DetailActivity.Companion.DATA_RESULT
import com.raytalktech.newsie.utils.DataMapper
import com.raytalktech.newsie.utils.PagerAdapter
import com.raytalktech.newsie.utils.ViewModelFactory
import com.raytalktech.newsie.utils.vo.Resource
import com.raytalktech.newsie.utils.vo.Status

class HomeFragment : Fragment() {

    //binding
    private var _contentBinding: ContentHomeFragmentBinding? = null
    private val binding get() = _contentBinding

    //viewModel
    private lateinit var viewModel: HomeViewModel

    //adapter
    private lateinit var sourceAdapter: SourceAdapter

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
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

            viewModel.getAllNewsData("Technology").observe(viewLifecycleOwner, getAllNewsData)
            viewModel.getAllSourceNewsData().observe(viewLifecycleOwner, sourceNewsObserver)
            viewModel.breakingNews().observe(viewLifecycleOwner, getBreakingNewsData)

            //Configure Tab
            val mListFragmentChip: ArrayList<Fragment> = arrayListOf()
            val mListChipCategory = resources.getStringArray(R.array.category_news)

            for (chipItem in mListChipCategory.indices)
                mListFragmentChip.add(ContentCategoriesFragment.newInstance(mListChipCategory[chipItem]))

            binding?.apply {
                val fragmentAdapter = PagerAdapter(
                    childFragmentManager,
                    mListChipCategory,
                    mListFragmentChip
                )
                viewpagerMain.adapter = fragmentAdapter
                tlCategoryNews.setupWithViewPager(viewpagerMain)
            }
        }
    }

    private val getBreakingNewsData = Observer<List<DataEntity>> { mDataList ->
        showLoading(true, 2)
        if (mDataList != null && mDataList.size > 1) {
            binding?.apply {
                val mData = mDataList[0]
                Glide.with(requireActivity()).load(mData.urlToImage).placeholder(
                    AppCompatResources.getDrawable(
                        requireActivity(),
                        R.drawable.baseline_broken_image_24
                    )
                ).error(
                    AppCompatResources.getDrawable(
                        requireActivity(),
                        R.drawable.loading_animation
                    )
                )
                    .into(ivItemFeatureCover)

                tvItemFeatureTitle.text = mData.title
                tvItemDateFeatureCover.text =
                    String.format("%s • Today", mData.name)


                Glide.with(requireActivity())
                    .load(mData.faviconUrl)
                    .placeholder(
                        AppCompatResources.getDrawable(
                            requireActivity(),
                            R.drawable.loading_animation
                        )
                    ).circleCrop()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .error(
                        AppCompatResources.getDrawable(
                            requireActivity(),
                            R.drawable.baseline_broken_image_24
                        )
                    )
                    .into(sivFavicon)

                cvContentFeaturedNews.setOnClickListener {
                    startActivity(
                        Intent(requireActivity(), DetailActivity::class.java)
                            .putExtra(DATA_RESULT, mData)
                    )
                }

                showLoading(false, 2)
            }
        } else {
            showLoading(false, 2)
        }
    }

    private val getAllNewsData = Observer<Resource<List<DataEntity>>> { result ->
        if (result != null) {
            when (result.status) {
                Status.LOADING -> {
                    showLoading(true, 1)
                }

                Status.SUCCESS -> {
                    //showLoading(false)
                }

                Status.ERROR -> {
                    showLoading(false, 1)
                }
            }
        }
    }

    private fun showLoading(state: Boolean, feature: Int) {
        binding?.apply {
            when (feature) {
                1 -> {
                    rvPublisher.isGone = state
                    shimmerPublisherNews.isVisible = state
                    showShimmer(state, shimmerPublisherNews)
                }

                2 -> {
                    sectionBreakingNews.isGone = state
                    cvContentFeaturedNews.isGone = state
                    tlCategoryNews.isGone = state
                    shimmerBreakingNews.isVisible = state
                    showShimmer(state, shimmerBreakingNews)
                }
            }
        }
    }

    private fun showShimmer(state: Boolean, shimmer: ShimmerFrameLayout) {
        if (state) {
            shimmer.startShimmer()
        } else {
            shimmer.stopShimmer()
            shimmer.hideShimmer()
        }
    }

    private val sourceNewsObserver = Observer<List<DataEntity>> { data ->
        showLoading(true, 1)
        if (data != null) {
            binding?.rvPublisher?.apply {
                layoutManager = LinearLayoutManager(
                    this@HomeFragment.context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                sourceAdapter = SourceAdapter { goToSourceListPubslisher(it.name) }
                adapter = sourceAdapter
                hasFixedSize()

                sourceAdapter.submitList(DataMapper.populateListPublisher(data))
                showLoading(false, 1)
            }
        } else {
            showLoading(false, 1)
        }
    }

    private fun goToSourceListPubslisher(publisherName: String) {
        //TODO: Go To List
    }
}