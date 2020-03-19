package com.ninos.mvp.utils

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import androidx.annotation.*
import androidx.core.content.ContextCompat
import com.ninos.mvp.app.Application

object ResourcesUtils {

    /**
     *根据[resId]获取String
     */
    fun getString(@StringRes resId: Int): String {
        return Application.instance.resources.getString(resId)
    }

    /**
     *根据[resId]获取String,并且使用[formatArgs]来替换格式化参数
     */
    fun getString(@StringRes resId: Int, vararg formatArgs: Any): String {
        return Application.instance.resources.getString(resId, formatArgs)
    }

    /**
     *根据[resId]获取StringArray
     */
    fun getStringArray(@ArrayRes resId: Int): Array<String> {
        return Application.instance.resources.getStringArray(resId)
    }

    /**
     *根据[resId]获取Integer
     */
    fun getInt(@IntegerRes resId: Int): Int {
        return Application.instance.resources.getInteger(resId)
    }

    /**
     *根据[resId]获取IntArray
     */
    fun getIntArray(@ArrayRes resId: Int): IntArray {
        return Application.instance.resources.getIntArray(resId)
    }

    /**
     *根据[resId]获取颜色
     */
    fun getColor(@ColorRes resId: Int): Int {
        return ContextCompat.getColor(Application.instance, resId)
    }

    /**
     *根据[resId]获取颜色StateList
     */
    fun getColorStateList(@ColorRes resId: Int): ColorStateList? {
        return ContextCompat.getColorStateList(Application.instance, resId)
    }

    /**
     *根据[resId]获取Drawable
     */
    fun getDrawable(@DrawableRes resId: Int): Drawable? {
        return ContextCompat.getDrawable(Application.instance, resId)
    }
}
