package com.intigral.assignmenthammad.di

import com.intigral.assignmenthammad.MyApplication

import com.intigral.assignmenthammad.ui.activities.mainactivity.MainActivityComponent
import com.intigral.assignmenthammad.ui.activities.mainactivity.MainActivityModule
import com.intigral.assignmenthammad.ui.activities.mainactivity.MainActivityRepository


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