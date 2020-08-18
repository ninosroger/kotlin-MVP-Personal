package com.ninos.mvp.repository.api

import com.ninos.mvp.repository.entity.SourceData
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

/**
 * @author Ninos
 */
interface ApiService {
    @FormUrlEncoded
    @POST("controller.php?script_name=user&act=act_login&remember=1")
    fun login(@Field("token") token: String, @Field("username") username: String, @Field("password") password: String): Observable<SourceData<String>>
}
