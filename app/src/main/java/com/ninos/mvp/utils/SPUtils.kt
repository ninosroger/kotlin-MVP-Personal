package com.ninos.mvp.utils

import android.content.Context
import android.content.SharedPreferences
import com.ninos.mvp.Application

class SPUtils {
    private lateinit var sp: SharedPreferences

    /**
     * Return the single [SPUtils] instance
     *
     * @return the single [SPUtils] instance
     */
    fun getInstance(): SPUtils = getInstance("", Context.MODE_PRIVATE)

    /**
     * Return the single [SPUtils] instance
     *
     * @param mode Operating mode.
     * @return the single [SPUtils] instance
     */
    fun getInstance(mode: Int): SPUtils = getInstance("", mode)

    /**
     * Return the single [SPUtils] instance
     *
     * @param spName The name of sp.
     * @return the single [SPUtils] instance
     */
    fun getInstance(spName: String): SPUtils = getInstance(spName, Context.MODE_PRIVATE)

    /**
     * Return the single [SPUtils] instance
     *
     * @param spName The name of sp.
     * @param mode   Operating mode.
     * @return the single [SPUtils] instance
     */
    fun getInstance(spName: String, mode: Int): SPUtils {
        return SPUtils(spName, mode)
    }

    constructor(spName: String) {
        sp = Application.instance.getSharedPreferences(spName, Context.MODE_PRIVATE)
    }

    constructor(spName: String, mode: Int) {
        sp = Application.instance.getSharedPreferences(spName, mode)
    }

    /**
     * Put the string value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    fun put(key: String, value: String?) {
        put(key, value, false)
    }

    /**
     * Put the string value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use [SharedPreferences.Editor.commit],
     * false to use [SharedPreferences.Editor.apply]
     */
    fun put(key: String, value: String?, isCommit: Boolean) {
        if (isCommit) {
            sp.edit().putString(key, value).commit()
        } else {
            sp.edit().putString(key, value).apply()
        }
    }

    /**
     * Return the string value in sp.
     *
     * @param key The key of sp.
     * @return the string value if sp exists or `""` otherwise
     */
    fun getString(key: String): String? = getString(key, "")

    /**
     * Return the string value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the string value if sp exists or `defaultValue` otherwise
     */
    fun getString(key: String, defaultValue: String?): String? = sp.getString(key, defaultValue)

    /**
     * Put the int value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    fun put(key: String, value: Int) {
        put(key, value, false)
    }

    /**
     * Put the int value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use [SharedPreferences.Editor.commit],
     * false to use [SharedPreferences.Editor.apply]
     */
    fun put(key: String, value: Int, isCommit: Boolean) {
        if (isCommit) {
            sp.edit().putInt(key, value).commit()
        } else {
            sp.edit().putInt(key, value).apply()
        }
    }

    /**
     * Return the int value in sp.
     *
     * @param key The key of sp.
     * @return the int value if sp exists or `-1` otherwise
     */
    fun getInt(key: String): Int = getInt(key, -1)

    /**
     * Return the int value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the int value if sp exists or `defaultValue` otherwise
     */
    fun getInt(key: String, defaultValue: Int): Int = sp.getInt(key, defaultValue)

    /**
     * Put the long value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    fun put(key: String, value: Long) {
        put(key, value, false)
    }

    /**
     * Put the long value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use [SharedPreferences.Editor.commit],
     * false to use [SharedPreferences.Editor.apply]
     */
    fun put(key: String, value: Long, isCommit: Boolean) {
        if (isCommit) {
            sp.edit().putLong(key, value).commit()
        } else {
            sp.edit().putLong(key, value).apply()
        }
    }

    /**
     * Return the long value in sp.
     *
     * @param key The key of sp.
     * @return the long value if sp exists or `-1` otherwise
     */
    fun getLong(key: String): Long = getLong(key, -1L)

    /**
     * Return the long value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the long value if sp exists or `defaultValue` otherwise
     */
    fun getLong(key: String, defaultValue: Long): Long = sp.getLong(key, defaultValue)

    /**
     * Put the float value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    fun put(key: String, value: Float) {
        put(key, value, false)
    }

    /**
     * Put the float value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use [SharedPreferences.Editor.commit],
     * false to use [SharedPreferences.Editor.apply]
     */
    fun put(key: String, value: Float, isCommit: Boolean) {
        if (isCommit) {
            sp.edit().putFloat(key, value).commit()
        } else {
            sp.edit().putFloat(key, value).apply()
        }
    }

    /**
     * Return the float value in sp.
     *
     * @param key The key of sp.
     * @return the float value if sp exists or `-1f` otherwise
     */
    fun getFloat(key: String): Float = getFloat(key, -1f)

    /**
     * Return the float value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the float value if sp exists or `defaultValue` otherwise
     */
    fun getFloat(key: String, defaultValue: Float): Float = sp.getFloat(key, defaultValue)

    /**
     * Put the boolean value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    fun put(key: String, value: Boolean) {
        put(key, value, false)
    }

    /**
     * Put the boolean value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use [SharedPreferences.Editor.commit],
     * false to use [SharedPreferences.Editor.apply]
     */
    fun put(key: String, value: Boolean, isCommit: Boolean) {
        if (isCommit) {
            sp.edit().putBoolean(key, value).commit()
        } else {
            sp.edit().putBoolean(key, value).apply()
        }
    }

    /**
     * Return the boolean value in sp.
     *
     * @param key The key of sp.
     * @return the boolean value if sp exists or `false` otherwise
     */
    fun getBoolean(key: String): Boolean = getBoolean(key, false)

    /**
     * Return the boolean value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the boolean value if sp exists or `defaultValue` otherwise
     */
    fun getBoolean(key: String, defaultValue: Boolean): Boolean = sp.getBoolean(key, defaultValue)

    /**
     * Put the set of string value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    fun put(key: String, value: Set<String?>?) {
        put(key, value, false)
    }

    /**
     * Put the set of string value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use [SharedPreferences.Editor.commit],
     * false to use [SharedPreferences.Editor.apply]
     */
    fun put(
        key: String,
        value: Set<String?>?,
        isCommit: Boolean
    ) {
        if (isCommit) {
            sp.edit().putStringSet(key, value).commit()
        } else {
            sp.edit().putStringSet(key, value).apply()
        }
    }

    /**
     * Return the set of string value in sp.
     *
     * @param key The key of sp.
     * @return the set of string value if sp exists
     * or `Collections.<String>emptySet()` otherwise
     */
    fun getStringSet(key: String): Set<String?>? = getStringSet(key, emptySet<String>())

    /**
     * Return the set of string value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the set of string value if sp exists or `defaultValue` otherwise
     */
    fun getStringSet(
        key: String,
        defaultValue: Set<String?>?
    ): Set<String?>? = sp.getStringSet(key, defaultValue)

    /**
     * Return all values in sp.
     *
     * @return all values in sp
     */
    fun getAll(): Map<String?, *>? = sp.all

    /**
     * Return whether the sp contains the preference.
     *
     * @param key The key of sp.
     * @return `true`: yes<br></br>`false`: no
     */
    operator fun contains(key: String): Boolean = sp.contains(key)

    /**
     * Remove the preference in sp.
     *
     * @param key The key of sp.
     */
    fun remove(key: String) {
        remove(key, false)
    }

    /**
     * Remove the preference in sp.
     *
     * @param key      The key of sp.
     * @param isCommit True to use [SharedPreferences.Editor.commit],
     * false to use [SharedPreferences.Editor.apply]
     */
    fun remove(key: String, isCommit: Boolean) {
        if (isCommit) {
            sp.edit().remove(key).commit()
        } else {
            sp.edit().remove(key).apply()
        }
    }

    /**
     * Remove all preferences in sp.
     */
    fun clear() {
        clear(false)
    }

    /**
     * Remove all preferences in sp.
     *
     * @param isCommit True to use [SharedPreferences.Editor.commit],
     * false to use [SharedPreferences.Editor.apply]
     */
    fun clear(isCommit: Boolean) {
        if (isCommit) {
            sp.edit().clear().commit()
        } else {
            sp.edit().clear().apply()
        }
    }
}