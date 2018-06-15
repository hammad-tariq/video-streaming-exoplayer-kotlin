package com.intigral.androidassignment.di

import android.app.Application
import android.util.Log
import com.intigral.assignmenthammad.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApiModule(val application: Application) {

    /*
 Header Initialization
 */
    @Provides
    @Singleton
    fun getHeaders(): HashMap<String, String> {
        val params = HashMap<String, String>()
        params.put("Content-Type", "application/json")
        return params
    }


    /*
     OkHttp Client initialztion
     */


    @Provides
    @Singleton
    protected fun provideOkHttpClientDefault(interceptor: HttpLoggingInterceptor, headers:HashMap<String,String>, timeout:Int): OkHttpClient {
        val okBuilder = OkHttpClient.Builder()
        okBuilder.addInterceptor(interceptor)
        okBuilder.addInterceptor { chain ->
            val request = chain.request()
            val builder = request.newBuilder()

            if (headers != null && headers.size > 0) {
                for ((key, value) in headers) {
                    builder.addHeader(key, value)
                    Log.e(key, value)
                }
            }
            chain.proceed(builder.build())
        }

//        val timeout = getTimeOut()
        okBuilder.connectTimeout(timeout.toLong(), TimeUnit.SECONDS)
        okBuilder.readTimeout(timeout.toLong(), TimeUnit.SECONDS)
        okBuilder.writeTimeout(timeout.toLong(), TimeUnit.SECONDS)

        return okBuilder.build()
    }

    /*
    Logging Interceptor to log Http Network Calls
    Logging Level Debug : Body
    Loggin Level Production : None
     */

    @Provides
    @Singleton
    protected fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return interceptor
    }

    /*
     Method to set connection time out.
     */

    @Provides
    fun getTimeOut(): Int {
        return 30
    }


    /*
     Method to return Http Builder
    */

    @Provides
    @Singleton
    fun provideRetrofitBuilder(okHttpClient: OkHttpClient): Retrofit {
        val builder = Retrofit.Builder()
                .baseUrl(BuildConfig.baseURL)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()

        return builder
    }
}