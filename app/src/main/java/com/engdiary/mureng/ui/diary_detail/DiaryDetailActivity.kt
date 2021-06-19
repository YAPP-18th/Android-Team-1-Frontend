package com.engdiary.mureng.ui.diary_detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import com.engdiary.mureng.BR
import com.engdiary.mureng.R
import com.engdiary.mureng.constant.IntentKey
import com.engdiary.mureng.data.Diary
import com.engdiary.mureng.databinding.ActivityDiaryDetailBinding
import com.engdiary.mureng.ui.base.BaseActivity
import com.engdiary.mureng.ui.social_detail.SocialDetailActivity
import com.engdiary.mureng.ui.write_diary.WriteDiaryContentActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiaryDetailActivity :
    BaseActivity<ActivityDiaryDetailBinding>(R.layout.activity_diary_detail) {
    override val viewModel: DiaryDetailViewModel by viewModels()

    private val diary: Diary?
        get() = intent.getParcelableExtra(IntentKey.DIARY)

    private val isDiaryEdited: String?
        get() = intent.getStringExtra(IntentKey.EDITED_DIARY.first)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.vm, viewModel)
        binding.viewModel = viewModel

        diary?.let { viewModel.setDiary(it) } ?: finish()
        showNewCookieDialog(isDiaryEdited)
        initToolbar(binding.toolbar, diary?.isMine)
        initButtons(binding)
        subscribeUi()
    }

    private fun showNewCookieDialog(diaryEdited: String?) {
        diaryEdited?.let {
            NewCookieDialog(this).show()
        }
    }

    private fun initToolbar(toolbar: MaterialToolbar, isMine: Boolean?) {
        toolbar.overflowIcon =
            ResourcesCompat.getDrawable(resources, R.drawable.icons_more_vertical, null)
        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.delete -> {
                    showDeleteDiaryDialog(this)
                }
                R.id.edit -> {
                    navigateToWriteDiaryContent()
                }
            }
            return@setOnMenuItemClickListener true
        }
        isMine?.let { toolbar.menu.setGroupVisible(R.id.menu_requiring_authenticated, it) }
    }


    private fun showDeleteDiaryDialog(context: Context) {
        MaterialAlertDialogBuilder(context)
            .setMessage(resources.getString(R.string.diary_detail_delete_dialog))
            .setPositiveButton(resources.getString(R.string.diary_detail_delete_dialog_accept)) { dialog, _ ->
                viewModel.deleteDiary()
            }
            .setNegativeButton(resources.getString(R.string.diary_detail_delete_dialog_cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun navigateToWriteDiaryContent() {
        Intent(this, WriteDiaryContentActivity::class.java)
            .putExtra(IntentKey.DIARY, viewModel.diary.value)
            .also { startActivity(it) }
    }

    private fun initButtons(binding: ActivityDiaryDetailBinding) {
        binding.textviewDiarydetailOtherresponse.setOnClickListener {
            navigateToSocialDetail()
        }
    }

    private fun navigateToSocialDetail() {
        viewModel.getQuestionId()?.let {
            Intent(this, SocialDetailActivity::class.java)
                .putExtra(IntentKey.QUESTION_ID, it)
        }?.also { startActivity(it) } ?: return
    }

    private fun subscribeUi() {
        viewModel.navigateToBefore.observe(this) {
            finish()
        }
    }
}
