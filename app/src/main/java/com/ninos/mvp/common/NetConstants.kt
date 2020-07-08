package com.ninos.mvp.common

object NetConstants {
    const val BASE_URL = "http://www.taoegoo.com/appex/"

    //内部使用错误码和错误信息
    const val ERROR_CODE = -1
    const val ERROR_CODE_UNKNOWN = 1000
    const val ERROR_CODE_REMOTE = 1002
    const val ERROR_CODE_PARSE = 1101
    const val ERROR_CODE_TIMEOUT = 1301
    const val ERROR_CODE_CANNOTCONNECT = 1302
    const val ERROR_CODE_CONNECTINTERRUPT = 1304

    //服务器返回错误码
    const val PACT_CODE_MISSING = "404"
}