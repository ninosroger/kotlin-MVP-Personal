package com.ninos.mvp.utils

import android.content.Context

object ScreenUtils {
    /**
     * dp -> px
     */
    fun dp2px(context: Context, dp: Float): Int {
        val density = context.resources.displayMetrics.density
        return (dp * density + 0.5f).toInt()
    }

    /**
     * px -> dp
     */
    fun px2dp(context: Context, px: Int): Float {
        val density = context.resources.displayMetrics.density
        return px / density
    }
}