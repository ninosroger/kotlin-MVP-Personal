package com.ninos.mvp.repository.component

import com.ninos.mvp.R
import com.ninos.mvp.repository.bean.ProcessData
import com.ninos.mvp.repository.bean.SourceData
import com.ninos.mvp.utils.Constants
import com.ninos.mvp.utils.ResourcesUtils
import org.json.JSONException
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.EOFException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.text.ParseException

/**
 * 用于简化请求代码
 */
inline fun <T : Any> Observable<SourceData<T>>.arashi(
    crossinline handle: (ProcessData<T>) -> Unit = {}
): Subscription = compose {
    it.flatMap { sourceData ->
        Observable.just(ProcessData(sourceData.code, sourceData.message, sourceData.content))
    }
}
    .subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
    .subscribe(
        {
            handle(it) },
        {
            handle(ProcessData(Constants.ERROR_CODE, handlerUnknownError(it).errorMessage, null)) }
    )

/**
 * 统一处理返回错误信息
 */
fun handleErrorCode(statusCode: String, message: String?): RepositoryException {
    return when (statusCode) {
        Constants.PACT_CODE_MISSING -> RepositoryException(
            Constants.ERROR_CODE_REMOTE,
            message ?: ResourcesUtils.getString(R.string.errorStr_cannotConnect)
        )
        else -> RepositoryException(
            Constants.ERROR_CODE_UNKNOWN,
            message ?: ResourcesUtils.getString(R.string.errorStr_unknown)
        )
    }
}

/**
 * 统一处理错误异常信息
 */
fun handlerUnknownError(error: Throwable): RepositoryException {
    return when (error) {
        is RepositoryException -> error
        is EOFException -> RepositoryException(
            Constants.ERROR_CODE_CONNECTINTERRUPT,
            ResourcesUtils.getString(R.string.errorStr_connectInterruption)
        )
        is ConnectException -> RepositoryException(
            Constants.ERROR_CODE_CANNOTCONNECT,
            ResourcesUtils.getString(R.string.errorStr_cannotConnect)
        )
        is SocketTimeoutException -> RepositoryException(
            Constants.ERROR_CODE_TIMEOUT,
            ResourcesUtils.getString(R.string.errorStr_timeOut)
        )
        is JSONException -> RepositoryException(
            Constants.ERROR_CODE_PARSE,
            ResourcesUtils.getString(R.string.errorStr_parse)
        )
        is ParseException -> RepositoryException(
            Constants.ERROR_CODE_PARSE,
            ResourcesUtils.getString(R.string.errorStr_parse)
        )
        else -> RepositoryException(
            Constants.ERROR_CODE_UNKNOWN,
            ResourcesUtils.getString(R.string.errorStr_unknown)
        )
    }
}
