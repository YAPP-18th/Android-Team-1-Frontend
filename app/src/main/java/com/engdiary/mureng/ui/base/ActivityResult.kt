package com.engdiary.mureng.ui.base

import android.app.Activity
import android.content.Intent

/**
 * [Activity.startActivityForResult]로 실행 후 결과 data class
 */
data class ActivityResult(val resultCode: Int, val data: Intent?)
