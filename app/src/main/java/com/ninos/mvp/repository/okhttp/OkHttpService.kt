package com.ninos.mvp.repository.okhttp

import com.ninos.mvp.repository.common.NetConstants
import com.ninos.mvp.repository.api.ApiService
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import java.util.concurrent.TimeUnit

/**
 * @author Ninos
 *
 * 线程安全的懒加载单例模式
 */
class OkHttpService {
    companion object {
        fun getService(): ApiService = Holder.retrofit.create(ApiService::class.java)
    }

    private object Holder {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(NetConstants.BASE_URL)
            .client(
                OkHttpClient()
                    .newBuilder()
                    .readTimeout(100, TimeUnit.SECONDS)
                    .writeTimeout(100, TimeUnit.SECONDS)
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }
}