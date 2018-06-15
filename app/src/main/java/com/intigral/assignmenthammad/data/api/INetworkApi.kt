package com.intigral.androidassignment.data.api

import com.intigral.androidassignment.data.api.pojo.response.LineUp
import io.reactivex.Observable
import retrofit2.http.GET

interface INetworkApi {

    @GET(Endpoints.posts)
    fun getLineUps(): Observable<LineUp>
}