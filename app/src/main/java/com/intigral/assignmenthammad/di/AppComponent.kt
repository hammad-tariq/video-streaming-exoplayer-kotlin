package com.intigral.androidassignment.di

import com.intigral.androidassignment.MyApplication
import com.intigral.androidassignment.ui.activities.mainactivity.MainActivityComponent
import com.intigral.androidassignment.ui.activities.mainactivity.MainActivityModule
import com.intigral.androidassignment.ui.activities.mainactivity.MainActivityRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApiModule::class,AppModule::class))
public interface AppComponent
{
    fun inject(app: MyApplication)
    fun inject(mMainActivityRepository: MainActivityRepository)
    fun plus(mainActivityModule: MainActivityModule): MainActivityComponent

}