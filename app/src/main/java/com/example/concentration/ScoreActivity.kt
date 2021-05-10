package com.example.concentration

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ScoreActivity : AppCompatActivity() {

    //ViewModel
    private val scoreViewModel: ConcentrationViewModel by lazy {
        ViewModelProvider(this).get(ConcentrationViewModel::class.java)
    }
    //MediaPlayer
    private lateinit var backgroundMusic: MediaPlayer

    // EditText
    private lateinit var userInputInitials : EditText

    //TextV View
    private lateinit var gameTypeText :TextView

    //Buttons
    private lateinit var backButton: ImageButton
    private lateinit var musicButton : ImageButton

    //Scores
    private lateinit var score1 :TextView
    private lateinit var score2 :TextView
    private lateinit var score3 :TextView
    private lateinit var score4 :TextView
    private lateinit var score5 :TextView
    private lateinit var score6 :TextView
    private lateinit var score7 :TextView
    private lateinit var score8 :TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        val numOfTiles = intent.getIntExtra(NUM_TILES, 0)
        scoreViewModel.setNumOfTiles(numOfTiles)

        val songTimeStamp = intent.getIntExtra(SONG_TIME_STAMP, 0)
        scoreViewModel.setSongTimeStamp(songTimeStamp)

        backgroundMusic = MediaPlayer.create(this, R.raw.lofi)
        if (!backgroundMusic.isPlaying) {
            backgroundMusic.seekTo(scoreViewModel.getSongTimeStamp())
            backgroundMusic.start()
            backgroundMusic.isLooping = true
        }

        val finalScore = intent.getIntExtra(FINAL_SCORE, 0)
        scoreViewModel.setScore(finalScore)

        musicButton = findViewById(R.id.music_button)
        musicButton.setBackgroundResource(R.drawable.audio)

        backButton = findViewById(R.id.back_button)
        userInputInitials = findViewById(R.id.user_initials)

        gameTypeText = findViewById(R.id.game_type)

        score1 = findViewById(R.id.score1)
        score2 = findViewById(R.id.score2)
        score3 = findViewById(R.id.score3)
        score4 = findViewById(R.id.score4)
        score5 = findViewById(R.id.score5)
        score6 = findViewById(R.id.score6)
        score7 = findViewById(R.id.score7)
        score8 = findViewById(R.id.score8)

        when(numOfTiles){
            4 -> gameTypeText.setText(R.string.four_cards)
            6 ->gameTypeText.setText(R.string.six_cards)
            8 ->gameTypeText.setText(R.string.eight_cards)
            10 ->gameTypeText.setText(R.string.ten_cards)
            12 ->gameTypeText.setText(R.string.twelve_cards)
            14 ->gameTypeText.setText(R.string.fourteen_cards)
            16 ->gameTypeText.setText(R.string.sixteen_cards)
            18 ->gameTypeText.setText(R.string.eighteen_cards)
            20 ->gameTypeText.setText(R.string.twenty_cards)
        }
        musicButton.setOnClickListener {
            if(backgroundMusic.isPlaying) {
                musicButton.setBackgroundResource(R.drawable.no_audio)
                backgroundMusic.pause()
            }
            else{
                musicButton.setBackgroundResource(R.drawable.audio)
                backgroundMusic.start()
            }
        }
        backButton.setOnClickListener{
            scoreViewModel.setSongTimeStamp(backgroundMusic.currentPosition)
            val songTimeStamp = scoreViewModel.getSongTimeStamp()
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra(SONG_TIME_STAMP, songTimeStamp)}
            startActivity(intent)
        }









    }
    override fun onResume() {
        super.onResume()
        if (!backgroundMusic.isPlaying) {
            backgroundMusic.prepare()
            backgroundMusic.seekTo(scoreViewModel.getSongTimeStamp())
            backgroundMusic.start()
            backgroundMusic.isLooping = true
        }

    }

    override fun onPause() {
        super.onPause()
        scoreViewModel.setSongTimeStamp(backgroundMusic.currentPosition)
        backgroundMusic.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        backgroundMusic.release()
    }
}
