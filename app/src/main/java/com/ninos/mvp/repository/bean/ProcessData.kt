package com.ninos.mvp.repository.bean

data class ProcessData<T>(
    var code: Int,
    var message: String,
    var content: T?
)