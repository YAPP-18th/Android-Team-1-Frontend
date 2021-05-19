package com.engdiary.mureng.ui.social_best_more

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.engdiary.mureng.data.AnswerData
import com.engdiary.mureng.data.BestAnswerData
import com.engdiary.mureng.data.QuestionData
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.base.BaseViewModel
import timber.log.Timber

abstract class BestAnswerViewModel constructor(
    murengRepository: MurengRepository
) : BaseViewModel(murengRepository) {
    /** 검색 결과 list */

    protected val _ansResults = MutableLiveData<List<BestAnswerData>>(listOf())
    open val ansResults: LiveData<List<BestAnswerData>> = _ansResults

    /** questionList 아이템뷰를 클릭시 동작하는 로직 */
    abstract fun answerItemClick(answerData: BestAnswerData)


    /** [_results] 내 특정 아이템을 추가한다. */
    fun addAnswerResult(answerData: BestAnswerData) {
        val results: MutableList<BestAnswerData> = _ansResults.value?.toMutableList() ?: mutableListOf()

        results.add(answerData)

        _ansResults.value = results
    }

    /** [_results] 내 특정 아이템을 [wineResult] 변경한다. */
    fun replaceAnswerResult(answerData: BestAnswerData) {
        val results: MutableList<BestAnswerData> = _ansResults.value?.toMutableList() ?: return

        val prevResult = (results.filter { answerData.answerTitle == it.answerTitle }).firstOrNull()

        prevResult?.let {
            val replaceIndex = results.indexOf(it)

            if (replaceIndex == -1) {
                Timber.i("$it isn't exist")
                return
            } else {
                results.removeAt(replaceIndex)
                results.add(replaceIndex, answerData)
                _ansResults.value = results
            }
        }
    }

    /** [_results] 내 특정 아이템을 삭제한다. */
    fun deleteAnswerResult(answerData: BestAnswerData) {
        val results: MutableList<BestAnswerData> = _ansResults.value?.toMutableList() ?: return

        val deleteIndex = results.indexOf(answerData)

        if (deleteIndex == -1) {
            Timber.i("$answerData isn't exist")
            return
        } else {
            results.removeAt(deleteIndex)
            _ansResults.value = results
        }
    }

    /** [_results] 내 특정 아이템의 좋아요 클릭 여부 내용만 바꿔준다. */
    fun toggleAnswerResult(prevAnswerData: BestAnswerData) {
        val results: MutableList<BestAnswerData> = _ansResults.value?.toMutableList() ?: return

        val replaceIndex = results.indexOf(prevAnswerData)

        if (replaceIndex == -1) {
            Timber.i("$prevAnswerData isn't exist")
            return
        } else {
            results.removeAt(replaceIndex)
            results.add(replaceIndex, prevAnswerData.copy(likeYn = prevAnswerData.clickedLikeYn))
            _ansResults.value = results
        }
    }

}
