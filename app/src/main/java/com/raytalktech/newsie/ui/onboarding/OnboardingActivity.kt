package com.raytalktech.newsie.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import com.raytalktech.newsie.MainActivity
import com.raytalktech.newsie.data.source.local.entity.ContentOnboarding
import com.raytalktech.newsie.data.source.local.entity.countryList
import com.raytalktech.newsie.data.source.local.entity.topicList
import com.raytalktech.newsie.databinding.ContentOnboardingActivityBinding
import com.raytalktech.newsie.utils.SharedPreferencesHelper
import com.raytalktech.newsie.utils.addChip

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ContentOnboardingActivityBinding
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ContentOnboardingActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnNext.text = "Next"
            tvTitleSettings.text = "Choose the country content"
        }
        sharedPreferencesHelper = SharedPreferencesHelper(this)

        setRecyclerView(countryList)
    }


    private fun setRecyclerView(mDataList: List<ContentOnboarding>) {
        mDataList.forEach {
            binding.chipBoarding.addChip(this, it.contentName)
        }

        binding.chipBoarding.setOnCheckedChangeListener { group, checkedId ->
            val mTitle = group?.findViewById<Chip>(checkedId)?.text
            val mData: ContentOnboarding =
                mDataList.find { it.contentName == mTitle.toString() } ?: ContentOnboarding(
                    "",
                    "",
                    ""
                )
            group?.findViewById<Chip>(checkedId)?.isChecked = true
            saveData(mData)
        }

    }

    private fun saveData(mData: ContentOnboarding) {
        if (mData.contentType == "country") {
            sharedPreferencesHelper.saveString("selected_country_id", mData.contentID)
            binding.chipBoarding.removeAllViews()
            setRecyclerView(topicList)
            binding.tvTitleSettings.text = "Choose the topic you prefer"
        } else {
            sharedPreferencesHelper.saveString("selected_topic_name", mData.contentName)
            binding.btnNext.apply {
                isEnabled = true
                setOnClickListener {
                    startActivity(
                        Intent(
                            this@OnboardingActivity,
                            MainActivity::class.java
                        )
                    )
                }
            }
        }
    }
}