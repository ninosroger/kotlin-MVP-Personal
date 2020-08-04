package com.ninos.mvp.repository.component

/**
 * @author Ninos
 */
data class RepositoryException
constructor(var errorCode: Int, var errorMessage: String = "RepositoryException") :
    Exception(errorMessage)
