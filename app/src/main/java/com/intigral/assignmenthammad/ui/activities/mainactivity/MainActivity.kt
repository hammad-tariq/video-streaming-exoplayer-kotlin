package com.intigral.androidassignment.ui.activities.mainactivity

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.intigral.androidassignment.MyApplication
import com.intigral.androidassignment.data.api.pojo.response.LineUp
import com.intigral.androidassignment.ui.adapter.ItemAwayLineUpAdapter
import com.intigral.androidassignment.ui.adapter.ItemHomeLineUpAdapter
import com.intigral.assignmenthammad.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.exo_simple_player_view.*
import javax.inject.Inject


class MainActivity : BaseActivity<ActivityMainBinding>(), View.OnClickListener {


    /*
 Variable Declaration
 */

    @Inject
    lateinit var mainActivityFactory: MainActivityFactory
    lateinit var mMainActivityViewModel: MainActivityViewModel
    var exoPlayer: ExoPlayer? = null

    var rvLeft: RecyclerView? = null
    var btnHomeLineUps: Button? = null
    var btnAwayLineUps: Button? = null
    var mPlayerCurrentPosition: Long? = 0
    var mPlayerCurrentWindow: Int? = 0
    var mPlayerReady: Boolean? = false
    lateinit var lineUp: LineUp
    var overlayVisible: Boolean = true


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        exo_overlay.visibility = View.GONE
        overlayVisible = false
        return super.onTouchEvent(event)
    }


    override fun onClick(v: View?) {

        when (v) {
            btnAwayLineUps -> {
                rvLeft?.adapter = ItemAwayLineUpAdapter(this@MainActivity, lineUp.Lineups.Data.AwayTeam)
            }
            btnHomeLineUps -> {
                rvLeft?.adapter = ItemHomeLineUpAdapter(this@MainActivity, lineUp.Lineups.Data.HomeTeam)
            }
            imgCloseFullScreen -> {
                finish()
            }
            imgShowHideOverlay -> {
                if (overlayVisible) {
                    exo_overlay.visibility = View.GONE
                    overlayVisible = false
                } else {
                    exo_overlay.visibility = View.VISIBLE
                    overlayVisible = true
                }
            }
        }
    }


    override fun init(savedInstanceState: Bundle?) {

        (application as MyApplication).createMainActivityComponent().inject(this)
        mMainActivityViewModel = ViewModelProviders.of(this, mainActivityFactory)[MainActivityViewModel::class.java]
        subscribe()
    }

    override fun setLayout(): Int {
        return R.layout.activity_main
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvLeft = findViewById(R.id.rv_lineup_left_exo) as? RecyclerView

        btnHomeLineUps = findViewById(R.id.btnHomeLineUpsExo) as? Button
        btnAwayLineUps = findViewById(R.id.btnAwayLineUpsExo) as? Button

        btnHomeLineUps?.setOnClickListener(this)
        btnAwayLineUps?.setOnClickListener(this)
        imgCloseFullScreen?.setOnClickListener(this)
        imgShowHideOverlay?.setOnClickListener(this)
    }

    /*
       Subscriber for Data Observers
   */


    fun subscribe() {
        var isDataLoading = object : Observer<Boolean> {
            override fun onChanged(t: Boolean?) {
                t?.let {
                    if (t) {
                        showLoading()
                    } else {
                        hideLoading()
                    }

                }
            }
        }


        var errorObserver = object : Observer<Throwable> {
            override fun onChanged(t: Throwable?) {

                t?.let {
                    loadError(it)
                }
            }
        }

        var lineUpResponse = object : Observer<LineUp> {
            override fun onChanged(t: LineUp?) {
                t?.let {
                    lineUp = t
                    rvLeft?.layoutManager = LinearLayoutManager(this@MainActivity)
                    rvLeft?.adapter = ItemHomeLineUpAdapter(this@MainActivity, lineUp.Lineups.Data.HomeTeam)
                    checkAppConfig(lineUp)
                }
            }
        }

        mMainActivityViewModel.getIsLoading().observe(this, isDataLoading)
        mMainActivityViewModel.getError().observe(this, errorObserver)
        mMainActivityViewModel.lineDataResponse().observe(this, lineUpResponse)
    }


    /*
       Combinning ExoPlayer with Activity LifeCycle
    */
    public override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
            initializePlayer()
        }
    }

    public override fun onResume() {
        super.onResume()
        hideSystemUi()
        if (Util.SDK_INT <= 23 || exoPlayer == null) {
            initializePlayer()
        }
    }

    public override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            releasePlayer()
        }
    }

    public override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            releasePlayer()
        }
    }

    public override fun onDestroy() {
        super.onDestroy()
        if (exoPlayer != null)
            exoPlayer?.release()
        exoPlayer = null
    }

    // This function is responsible to initialize ExoPlayer.
    private fun initializePlayer() {
        if (exoPlayer == null) {
            exoPlayer = ExoPlayerFactory.newSimpleInstance(
                    DefaultRenderersFactory(this),
                    DefaultTrackSelector(), DefaultLoadControl())
            video_view?.player = exoPlayer
            video_view?.player?.playWhenReady = true
        }

        val uri = Uri.parse(getString(R.string.exoplayer_media_source))
        exoPlayer?.prepare(buildMediaSource(uri), false, false)
        video_view?.player?.seekTo(mPlayerCurrentWindow!!, mPlayerCurrentPosition!!)
    }

    // This function is responsible to relase framework resources when not needed.
    private fun releasePlayer() {
        if (exoPlayer != null) {
            mPlayerCurrentPosition = exoPlayer?.currentPosition
            mPlayerCurrentWindow = exoPlayer?.currentWindowIndex
            mPlayerReady = exoPlayer?.playWhenReady
            exoPlayer?.release()
        }
    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        return HlsMediaSource.Factory(
                DefaultHttpDataSourceFactory("exoplayer"))
                .setAllowChunklessPreparation(true)
                .createMediaSource(uri)
    }

    // To have full screen streeming expereince in immersive mode.
    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        video_view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }

    private fun checkAppConfig(line: LineUp) {
        var rvRight: RecyclerView? = null
        val config = this@MainActivity.getResources().getConfiguration()
        if (config.smallestScreenWidthDp >= 600) {
            rvRight = findViewById(R.id.rv_lineup_right_exo) as? RecyclerView
            rvRight?.layoutManager = LinearLayoutManager(this@MainActivity)
            rvRight?.adapter = ItemAwayLineUpAdapter(this@MainActivity, line.Lineups.Data.AwayTeam)
        }
    }
}
