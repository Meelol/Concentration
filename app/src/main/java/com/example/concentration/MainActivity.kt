package com.example.concentration

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider


const val NUM_TILES = "com.example.concentration.NUMBER_OF_TILES"
const val SONG_TIME_STAMP = "com.example.concentration.SONG_TIME_STAMP"

class MainActivity : AppCompatActivity() {

    //MediaPlayer
    private lateinit var backgroundMusic :MediaPlayer

    //Buttons
    private lateinit var playButton :Button
    private lateinit var musicButton :ImageButton

    // EditText
    private lateinit var userInputText :EditText

    //ViewModel
    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playButton = findViewById(R.id.play_button)
        musicButton = findViewById(R.id.music_button)
        musicButton.setBackgroundResource(R.drawable.audio)
        userInputText = findViewById(R.id.number_of_tiles)

        val songTimeStamp = intent.getIntExtra(SONG_TIME_STAMP,0)

        backgroundMusic = MediaPlayer.create(this, R.raw.lofi)
        mainViewModel.setTimeStamp(songTimeStamp)
        if(!backgroundMusic.isPlaying){
            backgroundMusic.seekTo(mainViewModel.getTimeStamp())
            backgroundMusic.start()
            backgroundMusic.isLooping = true
        }

        playButton.setOnClickListener {
            val inputValue = userInputText.text.toString().toIntOrNull()
            //Start GameActivity
            val correctInput = checkInput(inputValue)
            if(correctInput) {
                if (inputValue != null) {
                    mainViewModel.setAmountOfTiles(inputValue)
                }
               val numOfTiles :Int = mainViewModel.getAmountOfTiles()
               mainViewModel.setTimeStamp(backgroundMusic.currentPosition)
               val songTimeStamp = mainViewModel.getTimeStamp()
               val intent = Intent(this, GameActivity::class.java).apply {
                   putExtra(NUM_TILES, numOfTiles)
                   putExtra(SONG_TIME_STAMP, songTimeStamp)}
               startActivity(intent)
            }
        }
        musicButton.setOnClickListener {
            if(backgroundMusic.isPlaying) {
                musicButton.setImageResource(R.drawable.no_audio)
                backgroundMusic.pause()
            }
            else{
                musicButton.setImageResource(R.drawable.audio)
                backgroundMusic.start()
            }
        }

        }

    private fun checkInput(inputValue :Int?) :Boolean {
        if (inputValue != null) {
            return if (inputValue < 4 || inputValue > 20 || (inputValue % 2) != 0) {
                val invalidInputMsg = when {
                    inputValue < 4 -> R.string.greater_than_4_toast
                    inputValue > 20 -> R.string.less_than_20_toast
                    else -> R.string.even_number_toast
                }
                Toast.makeText(this, invalidInputMsg, Toast.LENGTH_SHORT).show()
                false
            } else{
                true
            }
        }
        else{
            val invalidInputMsg = R.string.invalid_input_toast
            Toast.makeText(this, invalidInputMsg, Toast.LENGTH_SHORT).show()
            return false
        }
    }

    override fun onResume(){
        super.onResume()
        if(!backgroundMusic.isPlaying){
            backgroundMusic.prepare()
            backgroundMusic.start()
            backgroundMusic.isLooping = true
        }

    }
    override fun onPause(){
        super.onPause()
        mainViewModel.setTimeStamp(backgroundMusic.currentPosition)
        backgroundMusic.stop()
    }
    override fun onDestroy(){
        super.onDestroy()
        backgroundMusic.release()
    }
}

