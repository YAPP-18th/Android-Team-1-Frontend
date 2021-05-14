package com.engdiary.mureng.ui.write_diary

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.engdiary.mureng.BR
import com.engdiary.mureng.R
import com.engdiary.mureng.databinding.ActivityWritingDiaryBinding
import com.engdiary.mureng.databinding.ExpandableparentHintWritingdiaryBinding
import com.engdiary.mureng.databinding.ExpandablesecondHintWritingdiaryBinding
import com.engdiary.mureng.ui.base.BaseActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.skydoves.expandablelayout.ExpandableLayout
import dagger.hilt.android.AndroidEntryPoint
import org.jetbrains.annotations.NotNull

@AndroidEntryPoint
class WritingDiaryContentActivity :
    BaseActivity<ActivityWritingDiaryBinding>(R.layout.activity_writing_diary_content) {
    override val viewModel by viewModels<WritingDiaryContentViewModel>()
    private var isHintOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.vm, viewModel)
        binding.viewModel = viewModel

        setOnHintClickListener(binding.expandableWritingdaryHint)

        binding.textviewWritingdiaryNext.setOnClickListener {
            viewModel.checkKoreanIsExist()
        }

        subscribeUi()
    }

    private fun setOnHintClickListener(expandableWritingdaryHint: ExpandableLayout) {
        val hintParentBinding =
            ExpandableparentHintWritingdiaryBinding.bind(expandableWritingdaryHint.parentLayout)
        val hintSecondBinding =
            ExpandablesecondHintWritingdiaryBinding.bind(expandableWritingdaryHint.secondLayout)

        expandableWritingdaryHint.setOnClickListener {
            toggleHint(expandableWritingdaryHint, hintParentBinding)
        }

        hintSecondBinding.root.setOnClickListener {
            toggleHint(expandableWritingdaryHint, hintParentBinding)
        }
    }

    private fun toggleHint(
        expandableWritingdaryHint: ExpandableLayout,
        hintParentBinding: ExpandableparentHintWritingdiaryBinding
    ) {
        if (isHintOpen) {
            hideHint(expandableWritingdaryHint, hintParentBinding)
            return
        }
        showHint(expandableWritingdaryHint, hintParentBinding)
    }


    private fun showHint(
        expandableWritingdaryHint: ExpandableLayout,
        hintBinding: ExpandableparentHintWritingdiaryBinding
    ) {
        isHintOpen = true
        Glide.with(this)
            .load(R.drawable.icons_bulb)
            .into(hintBinding.imageviewWritingdiaryIconbulb)
        Glide.with(this)
            .load(R.drawable.writingdiary_hintarrow)
            .into(hintBinding.imageviewWritingdiaryIconhintarrow)
        expandableWritingdaryHint.expand()
    }

    private fun hideHint(
        expandableWritingdaryHint: ExpandableLayout,
        hintBinding: @NotNull ExpandableparentHintWritingdiaryBinding
    ) {
        isHintOpen = false
        Glide.with(this)
            .load(R.drawable.icons_bulb_checked)
            .into(hintBinding.imageviewWritingdiaryIconbulb)
        Glide.with(this)
            .load(R.drawable.writingdiary_hintarrow_up)
            .into(hintBinding.imageviewWritingdiaryIconhintarrow)
        expandableWritingdaryHint.collapse()
    }

    private fun subscribeUi() {
        viewModel.showKoreanExistDialog.observe(this) {
            showLogoutDialog(this)
        }

        viewModel.navigateToWritingDiaryImage.observe(this) {
            navigateToWritingDiaryImage()
        }

        viewModel.hints.observe(this) { hints ->
            hints?.let { initHintAdapter(binding.expandableWritingdaryHint, HintAdapter(it)) }
        }
    }

    private fun initHintAdapter(
        expandableWritingdaryHint: ExpandableLayout,
        hintAdapter: HintAdapter
    ) {
        val hintBinding =
            ExpandablesecondHintWritingdiaryBinding.bind(expandableWritingdaryHint.secondLayout)
        hintBinding.root.setOnClickListener { }
        hintBinding.hints.adapter = hintAdapter
        hintAdapter.notifyDataSetChanged()
    }

    private fun navigateToWritingDiaryImage() {
        Intent(this, WritingDiaryImageActivity::class.java)
            .also { startActivity(it) }
    }

    private fun showLogoutDialog(context: Context) {
        MaterialAlertDialogBuilder(context)
            .setMessage(resources.getString(R.string.writingdiary_korean_alert_message))
            .setPositiveButton(resources.getString(R.string.writingdiary_korean_alert_message_accept)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}
