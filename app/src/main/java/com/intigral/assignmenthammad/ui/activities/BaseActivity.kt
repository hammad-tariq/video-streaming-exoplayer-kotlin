package com.intigral.androidassignment.ui.activities.mainactivity

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.net.Uri
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.intigral.androidassignment.utils.DialogeUtils
import com.intigral.androidassignment.utils.ProgressDialogeInterface
import com.intigral.assignmenthammad.R
import io.vrinda.kotlinpermissions.PermissionsActivity
import kotlinx.android.synthetic.main.activity_main.*


abstract class BaseActivity<T: ViewDataBinding>: PermissionsActivity(), ProgressDialogeInterface
{

    protected var isActive=false;

    protected lateinit var viewDataBinding: T

    var userIsInteracting=false

    companion object {
        private lateinit var mProgressDialog: ProgressDialog

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding= DataBindingUtil.setContentView(this,setLayout())
        mProgressDialog = ProgressDialog(this)
        init(savedInstanceState)
    }


    override fun onPostResume() {
        super.onPostResume()
        isActive=true
    }

    override fun onPause() {
        super.onPause()
        isActive=false
    }



    override fun onDestroy() {
        super.onDestroy()
        System.gc()
        System.runFinalization()
    }






    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val v = currentFocus
        if (v is EditText) {
            val scoops = IntArray(2)
            v.getLocationOnScreen(scoops)
            val x = event.rawX + v.left - scoops[0]
            val y = event.rawY + v.top - scoops[1]

            if (event.action == MotionEvent.ACTION_UP && (x < v.left || x >= v.right || y < v.top || y > v
                            .bottom)) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v
                        .windowToken, 0)
            }
        }
        return super.dispatchTouchEvent(event)
    }


    abstract fun init(savedInstanceState: Bundle?);



    @LayoutRes
    abstract fun setLayout():Int




    override fun showLoading() {

        if(!mProgressDialog.isShowing)
        {
            showProgress(R.string.loading, null)

        }

    }



    override fun hideLoading() { dismissProgress() }




    fun showProgress(msgResId: Int, keyListener: DialogInterface.OnKeyListener?) {
        if (isFinishing) return
        mProgressDialog = ProgressDialog(this)
        mProgressDialog.setIndeterminate(true)
        mProgressDialog.setCancelable(false)

        if (mProgressDialog.isShowing()) { return }

        if (msgResId != 0) { mProgressDialog.setMessage(resources.getString(msgResId)) }

        if (keyListener != null) {mProgressDialog.setOnKeyListener(keyListener)
        } else { mProgressDialog.setCancelable(false) }
        mProgressDialog.show()
    }


    fun dismissProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing) {
            mProgressDialog.dismiss()
        }
    }

    /**
     * show progress dialog.
     *
     * @param msgResId
     */
    fun showProgress(msgResId: Int) {
        showProgress(msgResId, null)
    }

    override fun loadError(e: Throwable){
        loadError(e.localizedMessage)
    }

    override fun loadError(msg: String){
        DialogeUtils.showErrorDialog(this,getString(R.string.error),msg);
    }
}