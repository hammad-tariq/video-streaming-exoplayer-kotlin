package com.intigral.assignmenthammad.ui.activities.mainactivity

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import com.intigral.assignmenthammad.SingleLiveEvent
import com.intigral.assignmenthammad.data.api.pojo.response.LineUp
import com.intigral.assignmenthammad.utils.applySchedulersKotlin
import io.reactivex.disposables.Disposable

class MainActivityViewModel(var mainActivityRepository: MainActivityRepository): ViewModel() {



    var errorThrowable=SingleLiveEvent<Throwable>()

    var disposable: Disposable?=null

    var isDataLoading= SingleLiveEvent<Boolean>()

    var myLineUpResponse=MutableLiveData<LineUp>()


    init {
        callLineUpApi()
    }

    fun callLineUpApi(){
        isDataLoading.value=true
        disposable=mainActivityRepository.getResponse()
                .compose(applySchedulersKotlin())
                .subscribe(  { result ->
                    isDataLoading.value=false
                    myLineUpResponse.postValue(result)
                },
                        { error ->
                            isDataLoading.value=false
                            errorThrowable.postValue(error)
                        })
    }


    fun lineDataResponse():LiveData<LineUp>{
        return myLineUpResponse
    }




    fun getIsLoading(): LiveData<Boolean> {
        return isDataLoading
    }




    fun getError(): LiveData<Throwable> {
        return errorThrowable
    }

    override fun onCleared() {
        if(disposable!=null) {
            disposable!!.dispose()
        }
        super.onCleared()
    }




}