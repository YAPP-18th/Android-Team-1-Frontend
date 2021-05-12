package com.engdiary.mureng.ui.social_myques

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.engdiary.mureng.data.QuestionData
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.base.BaseViewModel
import timber.log.Timber

/**
 * questionList 에 대한 관리 변수 및 함수들을 관리하고 있으며 추상클래스로 두어야 한다.
 *
 */
abstract class QuestionViewModel constructor(
    murengRepository: MurengRepository
) : BaseViewModel(murengRepository) {
    /** 검색 결과 list */
    protected val _results = MutableLiveData<List<QuestionData>>(listOf())
    open val results: LiveData<List<QuestionData>> = _results

    private val _wineImg = MutableLiveData<Int>()
    val wineImg : LiveData<Int> = _wineImg

    /** questionList 아이템뷰를 클릭시 동작하는 로직 */
    abstract fun questionItemClick(questionData: QuestionData)


    /** [_results] 내 특정 아이템을 추가한다. */
    fun addQuestionResult(questionData: QuestionData) {
        val results: MutableList<QuestionData> = _results.value?.toMutableList() ?: mutableListOf()

        results.add(questionData)

        _results.value = results
    }

    /** [_results] 내 특정 아이템을 [wineResult] 변경한다. */
    fun replaceQuestionResult(questionData: QuestionData) {
        val results: MutableList<QuestionData> = _results.value?.toMutableList() ?: return

        val prevResult = (results.filter { questionData.quesTitle == it.quesTitle }).firstOrNull()

        prevResult?.let {
            val replaceIndex = results.indexOf(it)

            if (replaceIndex == -1) {
                Timber.i("$it isn't exist")
                return
            } else {
                results.removeAt(replaceIndex)
                results.add(replaceIndex, questionData)
                _results.value = results
            }
        }
    }

    /** [_results] 내 특정 아이템을 삭제한다. */
    fun deleteQuestionResult(questionData: QuestionData) {
        val results: MutableList<QuestionData> = _results.value?.toMutableList() ?: return

        val deleteIndex = results.indexOf(questionData)

        if (deleteIndex == -1) {
            Timber.i("$questionData isn't exist")
            return
        } else {
            results.removeAt(deleteIndex)
            _results.value = results
        }
    }
}
