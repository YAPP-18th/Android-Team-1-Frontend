package com.engdiary.mureng.ui.write_diary

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.engdiary.mureng.BR
import com.engdiary.mureng.R
import com.engdiary.mureng.constant.IntentKey
import com.engdiary.mureng.data.DiaryContent
import com.engdiary.mureng.databinding.ActivityWriteDiaryImageBinding
import com.engdiary.mureng.ui.base.BaseActivity
import com.engdiary.mureng.ui.diary_detail.DiaryDetailActivity
import com.engdiary.mureng.util.WindowLengthCalculator
import com.engdiary.mureng.util.dpToPx
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class WriteDiaryImageActivity :
    BaseActivity<ActivityWriteDiaryImageBinding>(R.layout.activity_write_diary_image) {

    override val viewModel by viewModels<WriteDiaryImageViewModel>()

    private val diaryImageAdapter by lazy {
        WriteDiaryImageAdapter(
            getDiaryImageItemSize(),
            WriteDiaryImageAdapter.OnGalleryClickListener {
                launchGallery()
            },
            WriteDiaryImageAdapter.OnClickListener { itemWritingDiaryImage ->
                viewModel.selectImage(itemWritingDiaryImage)
            })
    }

    private fun getDiaryImageItemSize(): Int =
        WindowLengthCalculator.getDisplayWidth(this) / DIARY_IMAGE_LIST_SPAN_COUNT

    private val resultLauncher = registerForActivityResult(GetContent())
    { uri: Uri? ->
        viewModel.selectGalleryImage(uri)
        Timber.d("(uri: $uri)")
    }

    private fun launchGallery() {
        resultLauncher.launch(SELECT_IMAGE_INPUT)
    }

    private val diaryContent: DiaryContent?
        get() = intent.getSerializableExtra("diaryContent") as DiaryContent?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.vm, viewModel)
        binding.viewModel = viewModel

        diaryContent?.let { viewModel.setDiaryContent(it) } ?: finish()

        initButtons(binding.buttonWritingdiaryimageBack, binding.textviewWritingdiaryWrite)
        initDiaryImages(binding.recyclerviewWritingdiaryimageCandidates)
        subscribeUi()
    }

    private fun initDiaryImages(recyclerviewWritingdiaryimageCandidates: RecyclerView) {
        recyclerviewWritingdiaryimageCandidates.adapter = diaryImageAdapter
        recyclerviewWritingdiaryimageCandidates.addItemDecoration(
            ItemDiaryImageDecoration(
                DIARY_IMAGE_LIST_SPAN_COUNT,
                DIARY_IMAGE_LIST_SPACING.dpToPx()
            )
        )
    }

    private fun initButtons(
        imageButtonWritingdiaryCancel: ImageButton,
        textviewWritingdiaryWrite: TextView
    ) {
        imageButtonWritingdiaryCancel.setOnClickListener { finish() }
        textviewWritingdiaryWrite.setOnClickListener { viewModel.requestWriteDiary() }
    }

    private fun subscribeUi() {
        viewModel.diaryImages.observe(this) {
            diaryImageAdapter.submitList(it)
        }

        viewModel.navigateToDiaryDetail.observe(this) { diary ->
            Intent(this, DiaryDetailActivity::class.java)
                .putExtra(IntentKey.DIARY, diary)
                .also { startActivity(it) }
        }
    }

    companion object {
        private const val SELECT_IMAGE_INPUT = "image/*"
        private const val DIARY_IMAGE_LIST_SPAN_COUNT = 3
        private const val DIARY_IMAGE_LIST_SPACING = 1
    }
}
