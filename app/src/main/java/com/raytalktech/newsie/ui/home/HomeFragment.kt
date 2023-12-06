package com.raytalktech.newsie.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.raytalktech.newsie.R
import com.raytalktech.newsie.data.source.local.entity.DataEntity
import com.raytalktech.newsie.databinding.ContentHomeFragmentBinding
import com.raytalktech.newsie.ui.detail.DetailActivity
import com.raytalktech.newsie.ui.detail.DetailActivity.Companion.DATA_RESULT
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
            }
        }
    }

    private val getAllNewsData = Observer<Resource<List<DataEntity>>> { result ->
        {
            when (result.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {}
                Status.ERROR -> {}
            }
        }
    }

    private val sourceNewsObserver = Observer<List<DataEntity>> { data ->
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

                sourceAdapter.submitList(data)
            }
        }
    }

    private fun goToSourceListPubslisher(publisherName: String) {
        //TODO: Go To List
    }
}