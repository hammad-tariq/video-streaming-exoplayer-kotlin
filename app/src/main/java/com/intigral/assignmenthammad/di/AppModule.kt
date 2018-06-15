package com.intigral.assignmenthammad.di

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.intigral.assignmenthammad.data.api.INetworkApi
import com.intigral.assignmenthammad.utils.Constants
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton


@Module
class AppModule(var application: Application)
{

    /*
     Method to return context of application
     */

    @Provides
    @Singleton
    fun provideContext(): Context
    {
        return application
    }


    /*
     Method to call for server apis
     */

    @Provides
    @Singleton
    fun getServerApi(retrofit: Retrofit): INetworkApi
    {
        return retrofit.create(INetworkApi::class.java)
    }


    /*
    Method to Check Network states
     */

    @Provides
    @Singleton
    @Named(Constants.isNetwork)
    fun isOnline(context: Context): Boolean {

        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo.isConnectedOrConnecting
    }
}