package com.raytalktech.newsie.ui.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class BookMarkFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mActivity = requireActivity() as AppCompatActivity
        mActivity.supportActionBar?.hide()
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}