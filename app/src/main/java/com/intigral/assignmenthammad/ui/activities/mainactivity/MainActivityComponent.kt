package com.intigral.assignmenthammad.ui.activities.mainactivity

import dagger.Subcomponent

@MainActivityScope
@Subcomponent(modules = arrayOf(MainActivityModule::class))
public interface MainActivityComponent {

    fun inject(mainActivity: MainActivity)
}