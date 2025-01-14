package com.example.smartgasmetering

import VideoAdapter
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.media3.exoplayer.ExoPlayer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartgasmetering.databinding.ActivityVideoBinding


class VideoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVideoBinding
    private val videoList = mutableListOf<Uri>()
    private var exoPlayer: ExoPlayer? = null
    private lateinit var adapter: VideoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Log.d("VideoActivity", "VideoActivity opened")
        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //NOva instanca ExoPlayer, sprejme trenutno alktivnost
        exoPlayer = ExoPlayer.Builder(this).build()


        adapter = VideoAdapter(videoList) { videoUri ->

            val intent = Intent(this, VideoPlayActivity::class.java).apply {
                putExtra("videoUri", videoUri.toString())
            }
            Log.d("VideoActivity", "Current videoList: $videoList")

            startActivity(intent)
        }
        binding.recyclerViewVideos.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewVideos.adapter = adapter


        val toolbar: androidx.appcompat.widget.Toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }


    val pickVideo =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                videoList.add(it)
                adapter.notifyItemInserted(videoList.size - 1)

            }
        }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()//klic na finish() izvede ob kliku na gumb za nazaj, kar zapre aktivnost.
                true
            }

            R.id.action_add_video -> {

                pickVideo.launch("video/*")
                true
            }

            else -> super.onOptionsItemSelected(item)//če bi imel več funkcionalnosti v meniju
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {//Pretvori XML datoteko menija v  UI elemente na toolbaru
        menuInflater.inflate(R.menu.menu_main_video, menu)
        return true
    }


}