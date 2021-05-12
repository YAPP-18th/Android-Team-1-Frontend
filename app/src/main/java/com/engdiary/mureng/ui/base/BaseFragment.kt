package com.engdiary.mureng.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 * BaseFragment
 *
 * @param layoutResId 레이아웃 Resource Id
 *
 * @property binding 바인딩 객체
 * @property viewModel ViewModel 객체
 * @property viewModelFactory ViewModel 을 사용하기 위해 반드시 필요한 ViewModelFactory

 */
abstract class BaseFragment<B : ViewDataBinding>(
    @LayoutRes val layoutResId: Int
) : Fragment() {
    lateinit var binding: B

    /**
     * [Dispatchers.Main]을 기본으로 사용하고
     * [onDestroy]에서 [cancel][CoroutineScope.cancel] 되는 코루틴 스코프
     *
     * @see [Coroutine 공식문서 Coroutine scope](https://kotlinlang.org/docs/reference/coroutines/coroutine-context-and-dispatchers.html#coroutine-scope)
     */
    val uiScope: CoroutineScope = MainScope()

    protected abstract val viewModel: BaseViewModel?
    val viewModelFactory: ViewModelProvider.AndroidViewModelFactory by lazy {
        ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
    }
}

/**
 * 특정 프레그먼트를 띄워준다.
 *
 * @param fm FragmentManager
 * @param id 프레그먼트가 들어갈 레이아웃 id
 */
fun BaseFragment<*>.navigate(fm: FragmentManager, @IdRes id: Int): Int {
    return fm.run {
        beginTransaction()
            .replace(id, this@navigate, this@navigate::class.simpleName)
            .commit()
    }
}

interface getFragmentManager {
    fun getFragmentManager() : FragmentManager
}
