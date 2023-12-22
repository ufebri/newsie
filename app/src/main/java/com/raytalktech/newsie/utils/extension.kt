package com.raytalktech.newsie.utils

import android.content.Context
import android.view.View
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.raytalktech.newsie.R

fun ChipGroup.addChip(context: Context, label: String) {

    Chip(context).apply {

        id = View.generateViewId()

        text = label

        isClickable = true

        isCheckable = true

        setChipSpacingHorizontalResource(R.dimen.dimen_16dp)

        isCheckedIconVisible = false

        isFocusable = true

        addView(this)
    }

}