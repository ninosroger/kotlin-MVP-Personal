package com.ninos.mvp.repository.component

/**
 * Created by ninos on 2019/1/8.
 */
data class RepositoryException
constructor(var errorCode: Int, var errorMessage: String = "RepositoryException") : Exception(errorMessage)
