package com.raytalktech.newsie.utils

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.raytalktech.newsie.R
import com.raytalktech.newsie.data.source.local.entity.ContentOnboarding
import com.raytalktech.newsie.databinding.BottomsheetGeneralFragmentBinding

class GeneralBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomsheetGeneralFragmentBinding? = null
    private val binding get() = _binding!!
    private var selectedOption: ContentOnboarding? = null

    interface OnItemSelectedListener {
        fun onItemSelected(selectedOption: ContentOnboarding)
    }

    private var onItemSelectedListener: OnItemSelectedListener? = null

    fun setOnItemSelectedListener(listener: OnItemSelectedListener) {
        onItemSelectedListener = listener
    }

    companion object {
        fun newInstance(
            options: List<ContentOnboarding>,
            idCurrentSelected: String?,
            title: String
        ): GeneralBottomSheet {
            val fragment = GeneralBottomSheet()
            val args = Bundle()
            args.putString("title", title)
            args.putParcelableArrayList("options", ArrayList(options))
            args.putString("idCurrentSelected", idCurrentSelected)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomsheetGeneralFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            // Access the BottomSheetBehavior from the view
            val behavior = BottomSheetBehavior.from(view.parent as View)

            // Set the initial state (Collapsed in this example)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED

            val options =
                arguments?.getParcelableArrayList<ContentOnboarding>("options") ?: emptyList()
            val idCurrentSelected = arguments?.getString("idCurrentSelected")
            val title = arguments?.getString("title")

            binding.tvTitleBottomSheet.text = title


            // Dynamically create radio buttons
            options.forEach { option ->
                val radioButton = RadioButton(requireActivity())
                radioButton.id = option.contentID.hashCode()
                radioButton.text = option.contentName
                radioButton.layoutDirection = View.LAYOUT_DIRECTION_RTL
                radioButton.textAlignment = View.TEXT_ALIGNMENT_TEXT_START

                // Set layout gravity to start
                radioButton.layoutParams = RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.MATCH_PARENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    gravity = Gravity.START
                }

                // Create a divider view
                val divider = View(context)
                divider.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    resources.getDimensionPixelSize(R.dimen.divider_height) // Set the height of the divider
                )
                divider.setBackgroundColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.shimmer_grey
                    )
                ) // Set the color of the divider


                binding.radioGroup.addView(radioButton)
                binding.radioGroup.addView(divider)

                // Add a click listener to handle state changes
                radioButton.setOnClickListener {
                    onRadioButtonClicked(option)
                }
            }

            // Pre-select the radio button based on the provided idCurrentSelected
            if (!idCurrentSelected.isNullOrEmpty()) {
                val selectedRadioButtonId =
                    options.find { it.contentID == idCurrentSelected }?.contentID.hashCode()
                selectedOption = options.find { it.contentID == idCurrentSelected }
                binding.radioGroup.check(selectedRadioButtonId)
            }

            binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
                // This listener will still be called when the state changes programmatically
                // Handle radio button changes here
                if (checkedId != View.NO_ID) {
                    // RadioButton clicked programmatically
                    selectedOption = options.find { it.contentID.hashCode() == checkedId }
                }
            }
        }
    }

    private fun onRadioButtonClicked(option: ContentOnboarding) {
        // Execute logic before changing the state
        // For example, you can prevent changing the state based on certain conditions
        if (someCondition()) {
            // Prevent changing the state
            return
        }

        // Update the selected option
        selectedOption = option

        // Update the UI or perform additional actions
        // ...
        binding.btnSave.setOnClickListener {
            onItemSelectedListener?.onItemSelected(option)
            dismiss()
        }

        // Manually update the checked state of the radio button
        updateRadioButtonState(option)
    }

    private fun someCondition(): Boolean {
        // Add your condition logic here
        return false
    }

    private fun updateRadioButtonState(option: ContentOnboarding) {
        // Manually update the checked state of the radio button
        for (i in 0 until binding.radioGroup.childCount) {
            val radioButton = binding.radioGroup.getChildAt(i) as? RadioButton
            if (radioButton?.id == option.contentID.hashCode()) {
                radioButton.isChecked = true
            } else {
                radioButton?.isChecked = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}