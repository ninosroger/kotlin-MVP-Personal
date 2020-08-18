package com.ninos.mvp.repository.entity

/**
 * @author Ninos
 */
data class ProcessData<T>(
    var code: Int,
    var message: String,
    var content: T?
)