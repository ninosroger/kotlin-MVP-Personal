package com.ninos.mvp.repository.api

import com.ninos.mvp.repository.bean.SourceData
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Created by ninos on 2019/1/8.
 */
interface ApiService {
    @FormUrlEncoded
    @POST("controller.php?script_name=user&act=act_login&remember=1")
    fun login(@Field("token") token: String, @Field("username") username: String, @Field("password") password: String): Observable<SourceData<String>>
}
