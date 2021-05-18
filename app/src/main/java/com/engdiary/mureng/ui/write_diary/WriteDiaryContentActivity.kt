package com.engdiary.mureng.ui.write_diary

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.engdiary.mureng.BR
import com.engdiary.mureng.R
import com.engdiary.mureng.data.DiaryContent
import com.engdiary.mureng.databinding.ActivityWriteDiaryContentBinding
import com.engdiary.mureng.databinding.ExpandableparentHintWritingdiaryBinding
import com.engdiary.mureng.ui.base.BaseActivity
import com.engdiary.mureng.util.dpToPx
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WriteDiaryContentActivity :
    BaseActivity<ActivityWriteDiaryContentBinding>(R.layout.activity_write_diary_content) {

    override val viewModel by viewModels<WriteDiaryContentViewModel>()

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

    private fun setOnHintClickListener(binding: ExpandableparentHintWritingdiaryBinding) {
        binding.root.setOnClickListener {
            viewModel.toggleHint()
        }
    }

    private fun subscribeUi() {
        viewModel.showKoreanExistDialog.observe(this) {
            showLogoutDialog(this)
        }

        viewModel.navigateToWritingDiaryImage.observe(this) {diaryContent ->
            navigateToWritingDiaryImage(diaryContent)
        }

        viewModel.hints.observe(this) { hints ->
            hints?.let { initHintAdapter(binding.hints, HintAdapter(it)) }
        }
    }

    private fun initHintAdapter(
        hints: RecyclerView,
        hintAdapter: HintAdapter
    ) {
        hints.setOnClickListener { viewModel.toggleHint() }
        hints.adapter = hintAdapter
        hints.addItemDecoration(HintDecoration(HINT_SPAN_COUNT, HINT_SPACING.dpToPx()))
        hintAdapter.notifyDataSetChanged()
    }

    private fun navigateToWritingDiaryImage(diaryContent: DiaryContent?) {
        if (diaryContent == null) return
        Intent(this, WriteDiaryImageActivity::class.java)
            .putExtra("diaryContent", diaryContent)
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

    companion object {
        private const val HINT_SPAN_COUNT = 2
        private const val HINT_SPACING = 20
    }
}
