package com.intigral.assignmenthammad.ui.activities.mainactivity


import com.intigral.assignmenthammad.MyApplication
import com.intigral.assignmenthammad.data.api.INetworkApi
import com.intigral.assignmenthammad.data.api.pojo.response.LineUp
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