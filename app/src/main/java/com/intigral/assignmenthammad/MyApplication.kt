package com.intigral.androidassignment

import android.app.Application
import com.intigral.androidassignment.di.ApiModule
import com.intigral.androidassignment.di.AppComponent
import com.intigral.androidassignment.di.AppModule
import com.intigral.androidassignment.ui.activities.mainactivity.MainActivityComponent
import com.intigral.androidassignment.ui.activities.mainactivity.MainActivityModule

open class MyApplication: Application(){

    /*
  Dagger App component with scoop of whole application
  */

    val appComponent: AppComponent by lazy {
        createComponent()
    }


    var mainActivityComponent: MainActivityComponent?=null


    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)

        mainActivityComponent=createMainActivityComponent()

    }

    /*
     Method to return the Dagger App component
     */

    fun createComponent(): AppComponent {
        return DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .apiModule(ApiModule(this))
                .build()

    }



    fun createMainActivityComponent():MainActivityComponent {
        return appComponent.plus(MainActivityModule())
    }

    fun releaseMainActivityComponent() {
        mainActivityComponent=null
    }



}