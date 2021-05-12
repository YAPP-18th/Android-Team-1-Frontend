package com.engdiary.mureng.ui.social_best

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.engdiary.mureng.data.AnswerData
import com.engdiary.mureng.data.QuestionData
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.base.BaseViewModel
import timber.log.Timber

abstract class AnswerViewModel constructor(
    murengRepository: MurengRepository
) : BaseViewModel(murengRepository) {
    /** 검색 결과 list */
    protected val _results = MutableLiveData<List<AnswerData>>(listOf())
    open val results: LiveData<List<AnswerData>> = _results

    /** questionList 아이템뷰를 클릭시 동작하는 로직 */
    abstract fun answerItemClick(answerData: AnswerData)


    /** [_results] 내 특정 아이템을 추가한다. */
    fun addAnswerResult(answerData: AnswerData) {
        val results: MutableList<AnswerData> = _results.value?.toMutableList() ?: mutableListOf()

        results.add(answerData)

        _results.value = results
    }

    /** [_results] 내 특정 아이템을 [wineResult] 변경한다. */
    fun replaceAnswerResult(answerData: AnswerData) {
        val results: MutableList<AnswerData> = _results.value?.toMutableList() ?: return

        val prevResult = (results.filter { answerData.ansTitle == it.ansTitle }).firstOrNull()

        prevResult?.let {
            val replaceIndex = results.indexOf(it)

            if (replaceIndex == -1) {
                Timber.i("$it isn't exist")
                return
            } else {
                results.removeAt(replaceIndex)
                results.add(replaceIndex, answerData)
                _results.value = results
            }
        }
    }

    /** [_results] 내 특정 아이템을 삭제한다. */
    fun deleteAnswerResult(answerData: AnswerData) {
        val results: MutableList<AnswerData> = _results.value?.toMutableList() ?: return

        val deleteIndex = results.indexOf(answerData)

        if (deleteIndex == -1) {
            Timber.i("$answerData isn't exist")
            return
        } else {
            results.removeAt(deleteIndex)
            _results.value = results
        }
    }
}
