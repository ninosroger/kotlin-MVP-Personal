package com.ninos.mvp.repository.api

import com.ninos.mvp.repository.entity.SourceData
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

/**
 * @author Ninos
 */
interface ApiService {
    @FormUrlEncoded
    @POST("")
    fun getHotGoodsList(
        @Field("page") page: Int,
        @Field("count") count: Int
    ): Observable<SourceData<String>>
}
