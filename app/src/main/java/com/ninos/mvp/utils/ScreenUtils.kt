package com.ninos.mvp.utils

import android.content.Context

/**
 * @author Ninos
 *
 * 不管是dp2px还是px2dp 因为结果做了强转会丢失精度，预期4.9应该四舍五入为5，但以下结果会是4
 * 4.1f.toInt() + "---" + 4.9f.toInt()
 * 4---4
 */

object ScreenUtils {
    /**
     * dp转px
     */
    fun dp2px(context: Context, dp: Float): Int {
        val density = context.resources.displayMetrics.density
        return (dp * density + 0.5f).toInt()
    }

    /**
     * px转dp
     */
    fun px2dp(context: Context, px: Int): Int {
        val density = context.resources.displayMetrics.density
        return (px / density + 0.5f).toInt()
    }
}