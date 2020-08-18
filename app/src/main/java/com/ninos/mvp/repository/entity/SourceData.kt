package com.ninos.mvp.repository.entity

import com.google.gson.annotations.SerializedName

/**
 * @author Ninos
 */
data class SourceData<T>(
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("content") val content: T
)