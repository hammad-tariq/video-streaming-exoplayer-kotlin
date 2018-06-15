package com.intigral.assignmenthammad.utils


internal interface ProgressDialogeInterface {

    fun showLoading()

    fun hideLoading()

    fun loadError(e: Throwable)

    fun loadError(msg: String)

}