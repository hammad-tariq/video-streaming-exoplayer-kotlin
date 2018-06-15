package com.intigral.assignmenthammad.ui.activities.mainactivity

import dagger.Module
import dagger.Provides

@Module
public class MainActivityModule{


    @Provides
    fun mainActivityRepository():MainActivityRepository
    {
        return MainActivityRepository()
    }

    @Provides
    fun mainActivityFactory(mainActivityRepository: MainActivityRepository):MainActivityFactory{

        return MainActivityFactory(mainActivityRepository)
    }

}