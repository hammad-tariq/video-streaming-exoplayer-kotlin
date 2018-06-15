package com.intigral.androidassignment.ui.activities.mainactivity


import com.intigral.androidassignment.MyApplication
import com.intigral.androidassignment.data.api.INetworkApi
import com.intigral.androidassignment.data.api.pojo.response.LineUp
import io.reactivex.Observable
import javax.inject.Inject


public class MainActivityRepository{

    @Inject
    lateinit var mNetworkSerice: INetworkApi


    init {

        MyApplication().appComponent.inject(this)

    }

    fun getResponse():Observable<LineUp>{

        return mNetworkSerice.getLineUps()
    }



}