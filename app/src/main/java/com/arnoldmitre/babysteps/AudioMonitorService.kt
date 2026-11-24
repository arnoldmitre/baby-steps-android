package com.arnoldmitre.babysteps

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.AudioPlaybackConfiguration
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper

class AudioMonitorService : Service() {

    private var playbackCallback: AudioManager.AudioPlaybackCallback? = null

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        registerCallback()
        return START_STICKY
    }

    private fun registerCallback() {
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as? AudioManager ?: return
        if (playbackCallback == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            playbackCallback = object : AudioManager.AudioPlaybackCallback() {
                override fun onPlaybackConfigChanged(configs: MutableList<AudioPlaybackConfiguration>?) {
                    super.onPlaybackConfigChanged(configs)
                    // Automatically trigger widget update when any audio starts or stops
                    BabyStepsWidgetProvider.updateAllWidgets(applicationContext)
                }
            }
            audioManager.registerAudioPlaybackCallback(playbackCallback!!, Handler(Looper.getMainLooper()))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as? AudioManager
        if (playbackCallback != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            audioManager?.unregisterAudioPlaybackCallback(playbackCallback!!)
            playbackCallback = null
        }
    }
}
