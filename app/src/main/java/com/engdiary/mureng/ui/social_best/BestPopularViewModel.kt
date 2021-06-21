package com.engdiary.mureng.ui.social_best

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.engdiary.mureng.data.response.DiaryNetwork
import com.engdiary.mureng.data.response.QuestionNetwork
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.base.BaseViewModel
import timber.log.Timber

abstract class BestPopularViewModel constructor(
    murengRepository: MurengRepository
) : BaseViewModel(murengRepository) {
    /** 검색 결과 list */
    protected val _quesResults = MutableLiveData<List<QuestionNetwork>>(listOf())
    open var quesResults: LiveData<List<QuestionNetwork>> = _quesResults

    protected val _ansResults = MutableLiveData<List<DiaryNetwork>>(listOf())
    open val ansResults: LiveData<List<DiaryNetwork>> = _ansResults


    abstract fun questionItemClick(questionData: QuestionNetwork)

    fun questionItemHeartClick(questionData: QuestionNetwork) {


    }

    fun addQuestionResult(questionData: QuestionNetwork) {
        val results: MutableList<QuestionNetwork> = _quesResults.value?.toMutableList() ?: mutableListOf()
        results.add(questionData)
        _quesResults.value = results
    }
    fun replaceQuestionResult(questionData: QuestionNetwork) {
        val results: MutableList<QuestionNetwork> = _quesResults.value?.toMutableList() ?: return

        val prevResult = (results.filter { questionData.content == it.content }).firstOrNull()

        prevResult?.let {
            val replaceIndex = results.indexOf(it)

            if (replaceIndex == -1) {
                Timber.i("$it isn't exist")
                return
            } else {
                results.removeAt(replaceIndex)
                results.add(replaceIndex, questionData)
                _quesResults.value = results
            }
        }
    }

    fun deleteQuestionResult(questionData: QuestionNetwork) {
        val results: MutableList<QuestionNetwork> = _quesResults.value?.toMutableList() ?: return
        val deleteIndex = results.indexOf(questionData)
        if (deleteIndex == -1) {
            Timber.i("$questionData isn't exist")
            return
        } else {
            results.removeAt(deleteIndex)
            _quesResults.value = results
        }
    }

    /** [_results] 내 특정 아이템의 좋아요 클릭 여부 내용만 바꿔준다. */
    fun toogleQuestionData(preQuestionData: QuestionNetwork) {
        val results: MutableList<QuestionNetwork> = _quesResults.value?.toMutableList() ?: return

        val replaceIndex = results.indexOf(preQuestionData)

        if (replaceIndex == -1) {
            Timber.i("$preQuestionData isn't exist")
            return
        } else {
            results.removeAt(replaceIndex)
            results.add(replaceIndex, preQuestionData.copy(likeYn = preQuestionData.clickedLikeYn))
            _quesResults.value = results
        }
    }


    abstract fun answerItemClick(answerData: DiaryNetwork)

    abstract fun answerItemHeartClick(answerData: DiaryNetwork)

    fun addAnswerResult(answerData: DiaryNetwork) {
        val results: MutableList<DiaryNetwork> = _ansResults.value?.toMutableList() ?: mutableListOf()
        results.add(answerData)
        _ansResults.value = results
    }

    fun replaceAnswerResult(answerData: DiaryNetwork) {
        val results: MutableList<DiaryNetwork> = _ansResults.value?.toMutableList() ?: return
        val prevResult = (results.filter { answerData.content == it.content }).firstOrNull()
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
    fun deleteAnswerResult(answerData: DiaryNetwork) {
        val results: MutableList<DiaryNetwork> = _ansResults.value?.toMutableList() ?: return
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
    fun toggleAnswerResult(preAnswerData: DiaryNetwork) {
        val results: MutableList<DiaryNetwork> = _ansResults.value?.toMutableList() ?: return

        val replaceIndex = results.indexOf(preAnswerData)

        if (replaceIndex == -1) {
            Timber.i("$preAnswerData isn't exist")
            return
        } else {
            results.removeAt(replaceIndex)
            results.add(replaceIndex, preAnswerData.copy(likeYn = preAnswerData.clickedLikeYn))
            _ansResults.value = results
        }
    }

}
