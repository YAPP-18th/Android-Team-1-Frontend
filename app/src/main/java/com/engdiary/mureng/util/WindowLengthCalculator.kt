package com.engdiary.mureng.util

import android.annotation.TargetApi
import android.app.Activity
import android.graphics.Insets
import android.graphics.Point
import android.graphics.Rect
import android.util.Size
import android.view.WindowInsets
import android.view.WindowManager


object WindowLengthCalculator {
    fun getDisplayWidth(activity: Activity): Int {
        val windowManager = activity.windowManager
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            getDisplayWidth(windowManager)
        } else {
            val display = windowManager.defaultDisplay
            val size = Point()
            display.getSize(size)
            size.x
        }
    }

    @TargetApi(android.os.Build.VERSION_CODES.R)
    private fun getDisplayWidth(windowManager: WindowManager): Int {
        val metrics = windowManager.currentWindowMetrics

        val insets = getUnnecessaryInset(metrics.windowInsets)
        val insetsWidth = insets.right + insets.left
        val insetsHeight = insets.top + insets.bottom

        val bounds: Rect = metrics.bounds
        val legacySize = Size(
            bounds.width() - insetsWidth,
            bounds.height() - insetsHeight
        )
        return legacySize.width
    }

    fun getDisplayHeight(activity: Activity): Int {
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            getDisplayHeight(activity.windowManager)
        } else {
            val display = activity.windowManager.defaultDisplay
            val size = Point()
            display.getSize(size)
            size.y
        }
    }

    @TargetApi(android.os.Build.VERSION_CODES.R)
    private fun getDisplayHeight(windowManager: WindowManager): Int {
        val metrics = windowManager.currentWindowMetrics

        val insets = getUnnecessaryInset(metrics.windowInsets)
        val insetsWidth = insets.right + insets.left
        val insetsHeight = insets.top + insets.bottom

        val bounds: Rect = metrics.bounds
        val legacySize = Size(
            bounds.width() - insetsWidth,
            bounds.height() - insetsHeight
        )
        return legacySize.height
    }


    private fun getUnnecessaryInset(windowInsets: WindowInsets): Insets {
        return windowInsets.getInsetsIgnoringVisibility(
            WindowInsets.Type.navigationBars()
                    or WindowInsets.Type.displayCutout()
        )
    }
}
