package com.intigral.androidassignment.ui.activities.mainactivity

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

public class MainActivityFactory(val mainActivityRepository: MainActivityRepository): ViewModelProvider.NewInstanceFactory(){



    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            return  MainActivityViewModel(mainActivityRepository) as T
        }
        throw IllegalStateException("View Model Class Cannot be casted")
    }

}