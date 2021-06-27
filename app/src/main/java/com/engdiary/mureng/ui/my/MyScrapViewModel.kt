package com.engdiary.mureng.ui.my

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.engdiary.mureng.di.AuthManager
import com.engdiary.mureng.network.MurengRepository
import com.engdiary.mureng.ui.home.ScrapViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MyScrapViewModel @Inject constructor(
        private val murengRepository: MurengRepository,
        private val authManager: AuthManager
) : ScrapViewModel(murengRepository) {

    init {
        viewModelScope.launch {
            val expressions = murengRepository.getTodayExpression()
            expressions?.let {
                for (item in it!!) {
                    addExpressionResult(item)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onCleared() {
        super.onCleared()
    }
}
