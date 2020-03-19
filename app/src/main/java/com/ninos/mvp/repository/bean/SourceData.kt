package com.ninos.mvp.repository.bean

import com.google.gson.annotations.SerializedName

data class SourceData<T>(
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("content") val content: T
)