package com.example.smartgasmetering

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.smartgasmetering.databinding.ActivityVideoPlayBinding

class VideoPlayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVideoPlayBinding
    private var exoPlayer: ExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityVideoPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar: androidx.appcompat.widget.Toolbar = binding.videoPlayToolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""


        val videoUri = intent.getStringExtra("videoUri")?.let { Uri.parse(it) }
        if (videoUri != null) {
            Log.d("VideoPlayActivity", "Playing video: $videoUri")

            playVideo(videoUri)
        } else {
            Toast.makeText(this, "No video found to play", Toast.LENGTH_SHORT).show()
            finish()
        }
    }


    private fun playVideo(videoUri: Uri) {
        exoPlayer = ExoPlayer.Builder(this).build().also { player ->
            //Also omogoča uporabo nove instance player znotraj bloka
            binding.playerView.player = player
            val mediaItem = MediaItem.fromUri(videoUri)
            //Ustvari MediaItem iz URI-ja, ki kaže na lokacijo videoposnetka
            player.setMediaItem(mediaItem)
            player.prepare()//Začne nalagati video iz URI-ja, Dekodira video in zvok, da pripravi predvajanje.
            player.playWhenReady = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer?.release()
        exoPlayer = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()//klic na finish() izvede ob kliku na gumb za nazaj, kar zapre aktivnost.
                true
            }


            else -> super.onOptionsItemSelected(item)//če bi imel več funkcionalnosti v meniju
        }
    }
}