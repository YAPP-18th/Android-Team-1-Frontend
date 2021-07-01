package com.engdiary.mureng.ui.my

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.engdiary.mureng.BR
import com.engdiary.mureng.R
import com.engdiary.mureng.constant.IntentKey
import com.engdiary.mureng.data.domain.Diary
import com.engdiary.mureng.databinding.FragmentRecordBinding
import com.engdiary.mureng.ui.base.BaseFragment
import com.engdiary.mureng.ui.diary_detail.DiaryDetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecordFragment : BaseFragment<FragmentRecordBinding>(R.layout.fragment_record) {
    override val viewModel: RecordViewModel by viewModels()
    private val recordAdapter by lazy {
        RecordAdapter(RecordAdapter.OnClickListener {
            navigateToDiaryDetail(it)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setVariable(BR.vm, viewModel)
        binding.lifecycleOwner = viewLifecycleOwner
        initRecords()
        subscribeUi()
    }

    private fun initRecords() {
        binding.recyclerviewRecords.adapter = recordAdapter
        binding.recyclerviewRecords.addItemDecoration(
            DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        )
    }

    private fun navigateToDiaryDetail(diary: Diary) {
        Intent(context, DiaryDetailActivity::class.java)
            .putExtra(IntentKey.DIARY, diary)
            .also { startActivity(it) }
    }

    private fun subscribeUi() {
        viewModel.records.observe(viewLifecycleOwner) {
            recordAdapter.submitList(it)
        }
    }
}