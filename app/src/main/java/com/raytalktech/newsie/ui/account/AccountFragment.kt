package com.raytalktech.newsie.ui.account

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.raytalktech.newsie.BuildConfig
import com.raytalktech.newsie.data.source.local.entity.ContentOnboarding
import com.raytalktech.newsie.data.source.local.entity.DataEntity
import com.raytalktech.newsie.data.source.local.entity.countryList
import com.raytalktech.newsie.data.source.local.entity.topicList
import com.raytalktech.newsie.databinding.ContentAccountFragmentBinding
import com.raytalktech.newsie.ui.detail.DetailActivity
import com.raytalktech.newsie.utils.GeneralBottomSheet
import com.raytalktech.newsie.utils.SharedPreferencesHelper

class AccountFragment : Fragment() {

    private var _contentBinding: ContentAccountFragmentBinding? = null
    private val binding get() = _contentBinding
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _contentBinding = ContentAccountFragmentBinding.inflate(inflater, container, false)
        val mActivity = requireActivity() as AppCompatActivity
        mActivity.supportActionBar?.show()
        return binding?.root
    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            sharedPreferencesHelper = SharedPreferencesHelper(requireActivity())
            val idCountrySelected =
                sharedPreferencesHelper.getString("selected_country_id") ?: ""
            val idTopicSelected = sharedPreferencesHelper.getString("selected_topic_name") ?: ""

            binding?.apply {
                tvAppVersion.text = "App version ${BuildConfig.VERSION_NAME}"
                tvPrivacy.setOnClickListener {
                    startActivity(
                        Intent(
                            requireActivity(),
                            DetailActivity::class.java
                        ).putExtra(DetailActivity.DATA_RESULT, mDataEntity)
                    )
                }
                tvLanguageMenu.setOnClickListener {
                    showBottomSheet(
                        countryList,
                        idCountrySelected,
                        "Content Country",
                        "selected_country_id"
                    )
                }
                tvTopicMenu.setOnClickListener {
                    showBottomSheet(
                        topicList,
                        idTopicSelected,
                        "Featured Topic",
                        "selected_topic_name"
                    )
                }
            }
        }
    }

    private fun showBottomSheet(
        options: List<ContentOnboarding>,
        idCurrentSelected: String,
        title: String,
        key: String
    ) {
        val bottomSheetFragment =
            GeneralBottomSheet.newInstance(options, idCurrentSelected, title)
        bottomSheetFragment.setOnItemSelectedListener(object :
            GeneralBottomSheet.OnItemSelectedListener {
            override fun onItemSelected(selectedOption: ContentOnboarding) {
                handleItemSelected(selectedOption, key)
            }
        })

        bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
    }

    private fun handleItemSelected(selectedOption: ContentOnboarding, key: String) {
        // Handle the selected option in your fragment
        sharedPreferencesHelper.saveString(key, selectedOption.contentID)
    }

    private val mDataEntity: DataEntity =
        DataEntity(
            id = "",
            name =
            "Privacy Policy",
            author = "",
            title = "Privacy Policy",
            description = "",
            url = "https://webview-super.web.app/newsie-privacy.html",
            urlToImage = "",
            publishedAt = "",
            content = "",
            faviconUrl = "",
            category = ""
        )
}