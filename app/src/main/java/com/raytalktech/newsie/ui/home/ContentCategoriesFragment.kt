package com.raytalktech.newsie.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.raytalktech.newsie.R
import com.raytalktech.newsie.data.source.local.entity.DataEntity
import com.raytalktech.newsie.databinding.ContentItemHomeFragmentBinding
import com.raytalktech.newsie.ui.detail.DetailActivity
import com.raytalktech.newsie.utils.ViewModelFactory
import com.raytalktech.newsie.utils.vo.Resource
import com.raytalktech.newsie.utils.vo.Status

class ContentCategoriesFragment : Fragment() {

    private var _binding: ContentItemHomeFragmentBinding? = null
    private val binding get() = _binding
    private lateinit var viewModel: HomeViewModel
    private var text = ""
    private lateinit var contentAdapter: ContentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            text = it.getString(ARG_TEXT) ?: ""
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ContentItemHomeFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

            if (resources.getStringArray(R.array.category_news).contains(text))
                viewModel.getAllNewsByCategory(text).observe(viewLifecycleOwner, categoriesListData)
        }
    }

    private val categoriesListData = Observer<Resource<List<DataEntity>>> { result ->
        when (result.status) {
            Status.LOADING -> {}
            Status.SUCCESS -> {
                if (result.data != null) {
                    binding?.rvContent?.apply {
                        layoutManager = LinearLayoutManager(
                            requireActivity(),
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                        contentAdapter = ContentAdapter { goToDetail(it) }
                        adapter = contentAdapter

                        contentAdapter.submitList(result.data)
                        hasFixedSize()
                    }
                } else {
                    Toast.makeText(requireActivity(), "error get data", Toast.LENGTH_LONG).show()
                }
            }

            Status.ERROR -> {
                Toast.makeText(requireActivity(), "error get data", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun goToDetail(mDataEntity: DataEntity) {
        startActivity(
            Intent(requireActivity(), DetailActivity::class.java)
                .putExtra(DetailActivity.DATA_RESULT, mDataEntity)
        )
    }

    companion object {
        private const val ARG_TEXT = "fragmentTAG"

        fun newInstance(text: String) =
            ContentCategoriesFragment().apply {
                arguments =
                    Bundle().apply {
                        putString(ARG_TEXT, text)
                    }
            }
    }
}