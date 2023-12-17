package com.raytalktech.newsie.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.raytalktech.newsie.data.source.local.entity.DataEntity
import com.raytalktech.newsie.databinding.ContentSearchFragmentBinding
import com.raytalktech.newsie.ui.detail.DetailActivity
import com.raytalktech.newsie.ui.home.ContentAdapter
import com.raytalktech.newsie.utils.ViewModelFactory
import com.raytalktech.newsie.utils.vo.Resource
import com.raytalktech.newsie.utils.vo.Status

class SearchFragment : Fragment() {

    private var _contentBinding: ContentSearchFragmentBinding? = null
    private val binding get() = _contentBinding
    private lateinit var viewModel: SearchViewModel
    private lateinit var contentAdapter: ContentAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _contentBinding = ContentSearchFragmentBinding.inflate(inflater, container, false)
        val mActivity = requireActivity() as AppCompatActivity
        mActivity.supportActionBar?.hide()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[SearchViewModel::class.java]

            binding?.apply {
                etSearch.doAfterTextChanged { result ->
                    if (!result.isNullOrBlank())
                        viewModel.getSearchListData(result.toString())
                            .observe(viewLifecycleOwner, getListSearchData)
                }
            }
        }
    }

    private val getListSearchData = Observer<Resource<List<DataEntity>>> { result ->
        when (result.status) {
            Status.LOADING -> showLoading(true)
            Status.SUCCESS -> {
                if (!result.data.isNullOrEmpty()) {
                    binding?.rvSearch?.apply {
                        layoutManager = LinearLayoutManager(this@SearchFragment.context)
                        contentAdapter = ContentAdapter { goToDetail(it) }
                        adapter = contentAdapter

                        contentAdapter.submitList(result.data)
                        hasFixedSize()
                    }
                } else {
                    binding?.apply {
                        rvSearch.isGone = true
                        animationView.isVisible = true
                    }
                }
                showLoading(false)
            }

            Status.ERROR -> showLoading(false)
        }
    }

    private fun showLoading(state: Boolean) {
        binding?.apply {
            if (state) {
                shimmerContentNews.isVisible = true
                shimmerContentNews.startShimmer()
                rvSearch.isGone = true
                animationView.isGone = true
            } else {
                shimmerContentNews.isGone = true
                shimmerContentNews.hideShimmer()
            }
        }
    }

    private fun goToDetail(mDataEntity: DataEntity) {
        startActivity(
            Intent(requireActivity(), DetailActivity::class.java)
                .putExtra(DetailActivity.DATA_RESULT, mDataEntity)
        )
    }
}