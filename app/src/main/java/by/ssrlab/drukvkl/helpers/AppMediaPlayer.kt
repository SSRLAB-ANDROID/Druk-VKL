package by.ssrlab.drukvkl.helpers

import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.widget.ImageButton
import by.ssrlab.drukvkl.MainActivity
import by.ssrlab.drukvkl.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException

object AppMediaPlayer {

    private var mediaPlayer: MediaPlayer? = null
    private var playerStatus = ""

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    fun initPlayer(activity: MainActivity, uri: Uri) {
        playerStatus = "play"
        mediaPlayer = MediaPlayer()

        try {
            mediaPlayer?.setDataSource(activity, uri)
            mediaPlayer?.prepare()
        } catch (e: IOException) {
            Log.e(PLAYER_EXCEPTION, e.message.toString())
        }
    }

    fun play(button: ImageButton) {
        scope.launch {
            when (playerStatus) {
                "play" -> {
                    playAudio(button)
                }
                "pause" -> {
                    pauseAudio(button)
                }
            }
        }
    }

    fun pause(button: ImageButton) {
        pauseAudio(button)
    }

    private fun playAudio(button: ImageButton) {
        try {
            mediaPlayer?.start()
            playerStatus = "pause"

            button.setImageResource(R.drawable.ic_pause)
            mediaPlayer?.setOnCompletionListener {
                button.setImageResource(R.drawable.ic_play)
                playerStatus = "play"
            }
        } catch (e: Exception) {
            Log.e(PLAYER_EXCEPTION, e.message.toString())
        }
    }

    private fun pauseAudio(button: ImageButton) {
        mediaPlayer?.pause()
        playerStatus = "play"

        button.setImageResource(R.drawable.ic_play)
    }
}