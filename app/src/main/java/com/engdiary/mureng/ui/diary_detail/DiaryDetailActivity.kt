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
import com.engdiary.mureng.ui.main.MainActivity
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

    private val isNewDiaryEdited: String?
        get() = intent.getStringExtra(IntentKey.NEW_DIARY.first)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.vm, viewModel)
        binding.viewModel = viewModel

        diary?.let { viewModel.setDiary(it) } ?: finish()
        showNewCookieDialog(isNewDiaryEdited)
        initToolbar(binding.toolbar, diary?.isMine)
        initButtons(binding)
        subscribeUi()
    }

    private fun showNewCookieDialog(isNewDiaryEdited: String?) {
        if (isNewDiaryEdited != null) {
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
        toolbar.setNavigationOnClickListener {
            navigateToHome()
        }
    }

    private fun navigateToHome() {
        Intent(this, MainActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            .also { startActivity(it) }
    }


    private fun showDeleteDiaryDialog(context: Context) {
        MaterialAlertDialogBuilder(context)
            .setTitle(resources.getString(R.string.diary_detail_delete_dialog))
            .setMessage(resources.getString(R.string.diary_detail_delete_diary_description))
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
        viewModel.getQuestion()?.let {
            Intent(this, SocialDetailActivity::class.java)
                .putExtra(IntentKey.QUESTION, it)
        }?.also { startActivity(it) } ?: return
    }

    private fun subscribeUi() {
        viewModel.navigateToBefore.observe(this) {
            navigateToHome()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        navigateToHome()
    }
}
