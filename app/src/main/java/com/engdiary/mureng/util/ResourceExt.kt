package com.engdiary.mureng.util

import androidx.core.content.ContextCompat
import com.engdiary.mureng.di.MurengApplication

/**
 * Resource Int 확장함수
 */

/** [Res Int 값][Int] 을 기반으로 색상값을 가져온다. */
fun Int.getColor() = ContextCompat.getColor(MurengApplication.getGlobalApplicationContext(), this)
