package com.intigral.assignmenthammad

import android.app.Application
import com.intigral.assignmenthammad.ui.activities.mainactivity.MainActivityComponent
import com.intigral.assignmenthammad.ui.activities.mainactivity.MainActivityModule
import com.intigral.assignmenthammad.di.ApiModule
import com.intigral.assignmenthammad.di.AppComponent
import com.intigral.assignmenthammad.di.AppModule


open class MyApplication: Application(){

    /*
  Dagger App component with scope of whole application
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