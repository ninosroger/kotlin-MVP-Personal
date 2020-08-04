package com.ninos.mvp.repository.component

import com.ninos.mvp.R
import com.ninos.mvp.repository.common.NetConstants
import com.ninos.mvp.repository.bean.ProcessData
import com.ninos.mvp.repository.bean.SourceData
import com.ninos.mvp.utils.ResourcesUtils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.json.JSONException
import java.io.EOFException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.text.ParseException

/**
 * @author Ninos
 */

/**
 * 简化网络请求代码
 * Observable的拓展方法
 * flatMap加工数据源
 * subscribe统一返回
 */
inline fun <T : Any> Observable<SourceData<T>>.arashi(
    crossinline handle: (ProcessData<T>) -> Unit = {}
): Disposable = compose {
    it.flatMap { sourceData ->
        Observable.just(ProcessData(sourceData.code, sourceData.message, sourceData.content))
    }
}
    .subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
    .subscribe(
        {
            handle(it)
        },
        {
            handle(ProcessData(NetConstants.ERROR_CODE, handlerUnknownError(it).errorMessage, null))
        }
    )

/**
 * 统一处理返回错误信息
 */
fun handleErrorCode(statusCode: String, message: String?): RepositoryException {
    return when (statusCode) {
        NetConstants.PACT_CODE_MISSING -> RepositoryException(
            NetConstants.ERROR_CODE_REMOTE,
            message ?: ResourcesUtils.getString(R.string.errorStr_cannotConnect)
        )
        else -> RepositoryException(
            NetConstants.ERROR_CODE_UNKNOWN,
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
            NetConstants.ERROR_CODE_CONNECTINTERRUPT,
            ResourcesUtils.getString(R.string.errorStr_connectInterruption)
        )
        is ConnectException -> RepositoryException(
            NetConstants.ERROR_CODE_CANNOTCONNECT,
            ResourcesUtils.getString(R.string.errorStr_cannotConnect)
        )
        is SocketTimeoutException -> RepositoryException(
            NetConstants.ERROR_CODE_TIMEOUT,
            ResourcesUtils.getString(R.string.errorStr_timeOut)
        )
        is JSONException -> RepositoryException(
            NetConstants.ERROR_CODE_PARSE,
            ResourcesUtils.getString(R.string.errorStr_parse)
        )
        is ParseException -> RepositoryException(
            NetConstants.ERROR_CODE_PARSE,
            ResourcesUtils.getString(R.string.errorStr_parse)
        )
        else -> RepositoryException(
            NetConstants.ERROR_CODE_UNKNOWN,
            ResourcesUtils.getString(R.string.errorStr_unknown)
        )
    }
}
