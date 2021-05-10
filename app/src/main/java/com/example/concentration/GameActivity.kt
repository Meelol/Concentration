package com.example.concentration

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlin.random.Random

const val FINAL_SCORE = "com.example.concentration.FINAL_SCORE"
class GameActivity : AppCompatActivity() {

    //ViewModel
    private val concentrationViewModel: ConcentrationViewModel by lazy {
        ViewModelProvider(this).get(ConcentrationViewModel::class.java)
    }

    //MediaPlayer
    private lateinit var backgroundMusic: MediaPlayer

    //Score TextView
    private lateinit var scoreText: TextView

    //Buttons
    private lateinit var musicButton: ImageButton
    private lateinit var backButton: ImageButton
    private lateinit var endGameButton: Button
    private lateinit var newGameButton: Button
    private lateinit var tryAgainButton: Button
    private lateinit var tiles: List<ImageButton>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val numOfTiles = intent.getIntExtra(NUM_TILES, 0)
        concentrationViewModel.setNumOfTiles(numOfTiles)
        val songTimeStamp = intent.getIntExtra(SONG_TIME_STAMP, 0)
        concentrationViewModel.setSongTimeStamp(songTimeStamp)

        backgroundMusic = MediaPlayer.create(this, R.raw.lofi)
        if (!backgroundMusic.isPlaying) {
            backgroundMusic.seekTo(concentrationViewModel.getSongTimeStamp())
            backgroundMusic.start()
            backgroundMusic.isLooping = true
        }

        scoreText = findViewById(R.id.score_text)
        scoreText.setText("Score: " + concentrationViewModel.getScore())

        musicButton = findViewById(R.id.music_button)
        musicButton.setBackgroundResource(R.drawable.audio)
        backButton = findViewById(R.id.back_button)
        endGameButton = findViewById(R.id.end_game_button)
        newGameButton = findViewById(R.id.new_game_button)
        tryAgainButton = findViewById(R.id.try_again_button)
        tiles = listOf(findViewById(R.id.Fruit1), findViewById(R.id.Fruit2), findViewById(R.id.Fruit3), findViewById(R.id.Fruit4),
                       findViewById(R.id.Fruit5), findViewById(R.id.Fruit6), findViewById(R.id.Fruit7), findViewById(R.id.Fruit8),
                       findViewById(R.id.Fruit9), findViewById(R.id.Fruit10), findViewById(R.id.Fruit11), findViewById(R.id.Fruit12),
                       findViewById(R.id.Fruit13), findViewById(R.id.Fruit14), findViewById(R.id.Fruit15), findViewById(R.id.Fruit16),
                       findViewById(R.id.Fruit17), findViewById(R.id.Fruit18), findViewById(R.id.Fruit19), findViewById(R.id.Fruit20))

        if(concentrationViewModel.getChosenFruits() != emptyMap<Int, Boolean>())
        {
            for(item in concentrationViewModel.getChosenTiles()){
                if(concentrationViewModel.attemptedMap[item] == true)
                {
                    tiles[item - 1].setImageResource(concentrationViewModel.fruits[concentrationViewModel.getChosenFruits()[item]]!!.fruit)
                    tiles[item - 1].visibility = View.VISIBLE
                    tiles[item - 1].isClickable = false
                }
                else{
                    tiles[item - 1].visibility = View.VISIBLE
                    tiles[item - 1].isClickable = true
                }
            }
            recoverGame(concentrationViewModel.getChosenTiles())
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
            concentrationViewModel.setSongTimeStamp(backgroundMusic.currentPosition)
            val songTimeStamp = concentrationViewModel.getSongTimeStamp()
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra(SONG_TIME_STAMP, songTimeStamp)}
            startActivity(intent)
        }
        endGameButton.setOnClickListener {
            val songTimeStamp = concentrationViewModel.getSongTimeStamp()
            val numOfTiles = concentrationViewModel.getNumOfTiles()
            val finalScore = concentrationViewModel.getScore()
            val intent = Intent(this, ScoreActivity::class.java).apply{
                putExtra(SONG_TIME_STAMP, songTimeStamp)
                putExtra(NUM_TILES, numOfTiles)
                putExtra(FINAL_SCORE, finalScore)
            }
            startActivity(intent)
        }
        newGameButton.setOnClickListener {
            concentrationViewModel.setScore(0)
            concentrationViewModel.setFirstAttempted(false)
            for(item in concentrationViewModel.getChosenTiles()){
                concentrationViewModel.attemptedMap.remove(item)
                concentrationViewModel.attemptedMap.put(item, false)
            }
            scoreText.setText("Score: " + concentrationViewModel.getScore())
            for(item in concentrationViewModel.getChosenTiles()) {
                tiles[item - 1].setImageResource(R.drawable.box)
                tiles[item - 1].isClickable = true
            }
            startNewConcentrationGame(concentrationViewModel.getKeys(), concentrationViewModel.getChosenTiles())

        }
        tryAgainButton.setOnClickListener {
            for(item in concentrationViewModel.getChosenTiles()){
                concentrationViewModel.attemptedMap.remove(item)
                concentrationViewModel.attemptedMap.put(item, false)
            }
            concentrationViewModel.setScore(0)
            scoreText.setText("Score: " + concentrationViewModel.getScore())
            for(item in concentrationViewModel.getChosenTiles()){
                tiles[item - 1].setImageResource(R.drawable.box)
                tiles[item - 1].isClickable = true
            }
            concentrationViewModel.setFirstAttempted(false)
            recoverGame(concentrationViewModel.getChosenTiles())


        }

        //Concentration Game
        if(concentrationViewModel.getChosenFruits() == emptyMap<Int, Int>())
        {
            when(numOfTiles) {
                4 -> {
                    val keys = mutableListOf(5, 8)
                    concentrationViewModel.setKeys(keys)
                    val selectedTiles = mutableListOf(1, 2, 3, 4)
                    concentrationViewModel.setChosenTiles(selectedTiles)
                    for (item in selectedTiles) {
                        tiles[item - 1].apply {
                            visibility = View.VISIBLE
                            isClickable = true
                        }
                    }
                    startNewConcentrationGame(concentrationViewModel.getKeys(), concentrationViewModel.getChosenTiles())
                }
                6 -> {
                    val keys = mutableListOf(2, 1, 7)
                    concentrationViewModel.setKeys(keys)
                    val selectedTiles = mutableListOf(1, 2, 3, 4, 5, 6)
                    concentrationViewModel.setChosenTiles(selectedTiles)
                    for (item in selectedTiles) {
                        tiles[item - 1].apply {
                            visibility = View.VISIBLE
                            isClickable = true
                        }
                    }
                    startNewConcentrationGame(concentrationViewModel.getKeys(), concentrationViewModel.getChosenTiles())
                }
                8 -> {
                    val keys = mutableListOf(3, 4, 6, 10)
                    concentrationViewModel.setKeys(keys)
                    val selectedTiles = mutableListOf(1, 2, 3, 4, 6, 7, 8)
                    concentrationViewModel.setChosenTiles(selectedTiles)
                    for (item in selectedTiles) {
                        tiles[item - 1].apply {
                            visibility = View.VISIBLE
                            isClickable = true
                        }
                    }
                    startNewConcentrationGame(concentrationViewModel.getKeys(), concentrationViewModel.getChosenTiles())
                }
                10 -> {
                    val keys = mutableListOf(9, 8, 6, 2, 4)
                    concentrationViewModel.setKeys(keys)
                    val selectedTiles = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                    concentrationViewModel.setChosenTiles(selectedTiles)
                    for (item in selectedTiles) {
                        tiles[item - 1].apply {
                            visibility = View.VISIBLE
                            isClickable = true
                        }
                    }
                    startNewConcentrationGame(concentrationViewModel.getKeys(), concentrationViewModel.getChosenTiles())
                }
                12 -> {
                    val keys = mutableListOf(1, 2, 4, 5, 7, 10)
                    concentrationViewModel.setKeys(keys)
                    val selectedTiles = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
                    concentrationViewModel.setChosenTiles(selectedTiles)
                    for (item in selectedTiles) {
                        tiles[item - 1].apply {
                            visibility = View.VISIBLE
                            isClickable = true
                        }
                    }
                    startNewConcentrationGame(concentrationViewModel.getKeys(), concentrationViewModel.getChosenTiles())
                }
                14 -> {
                    val keys = mutableListOf(2, 1, 3, 5, 6, 9, 10)
                    concentrationViewModel.setKeys(keys)
                    val selectedTiles = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14)
                    concentrationViewModel.setChosenTiles(selectedTiles)
                    for (item in selectedTiles) {
                        tiles[item - 1].apply {
                            visibility = View.VISIBLE
                            isClickable = true
                        }
                    }
                    startNewConcentrationGame(concentrationViewModel.getKeys(), concentrationViewModel.getChosenTiles())
                }
                16 -> {
                    val keys = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8)
                    concentrationViewModel.setKeys(keys)
                    val selectedTiles = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
                    concentrationViewModel.setChosenTiles(selectedTiles)
                    for (item in selectedTiles) {
                        tiles[item - 1].apply {
                            visibility = View.VISIBLE
                            isClickable = true
                        }
                    }
                    startNewConcentrationGame(concentrationViewModel.getKeys(), concentrationViewModel.getChosenTiles())
                }
                18 -> {
                    val keys = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
                    concentrationViewModel.setKeys(keys)
                    val selectedTiles = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18)
                    concentrationViewModel.setChosenTiles(selectedTiles)
                    for (item in selectedTiles) {
                        tiles[item - 1].apply {
                            visibility = View.VISIBLE
                            isClickable = true
                        }
                    }
                    startNewConcentrationGame(concentrationViewModel.getKeys(), concentrationViewModel.getChosenTiles())
                }
                20 -> {
                    val keys = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                    concentrationViewModel.setKeys(keys)
                    val selectedTiles = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20)
                    concentrationViewModel.setChosenTiles(selectedTiles)
                    for (item in selectedTiles) {
                        tiles[item - 1].apply {
                            visibility = View.VISIBLE
                            isClickable = true
                        }
                    }
                    startNewConcentrationGame(concentrationViewModel.getKeys(), concentrationViewModel.getChosenTiles())
                }
            }
        }
    }
    fun startNewConcentrationGame(keys: MutableList<Int>, selectedTiles: MutableList<Int>) {
        val tempList:MutableList<Int> = mutableListOf()
        selectedTiles.forEach{ tile ->
            tempList.add(tile)
        }
        var chosenFruits : MutableMap<Int, Int> = mutableMapOf()
        for(item in keys){
            for(i in 1..2)
            {
                if(tempList.lastIndex != 0){
                    var randomTile = Random.nextInt(0, tempList.size - 1)
                    chosenFruits[tempList[randomTile]] = item
                    tempList.remove(tempList[randomTile])
                }
                else
                    chosenFruits[tempList[0]] = item
            }
        }
        concentrationViewModel.setChosenFruits(chosenFruits)
        for(item in selectedTiles){
                if(tiles[item - 1].visibility == View.VISIBLE && tiles[item - 1].isClickable) {
                tiles[item - 1].setOnClickListener {
                    if(!concentrationViewModel.getFirstAttempted()){
                        concentrationViewModel.setFirstFruit(concentrationViewModel.getChosenFruits()[item]!!)
                        tiles[item - 1].isClickable = false
                        tiles[item - 1].setImageResource(concentrationViewModel.fruits[concentrationViewModel.getFirstFruit()]!!.fruit)
                        concentrationViewModel.attemptedMap.remove(item)
                        concentrationViewModel.attemptedMap.put(item, true)
                        concentrationViewModel.setFirstAttempted(true)
                    }
                    else{

                        concentrationViewModel.setSecondFruit(concentrationViewModel.getChosenFruits()[item]!!)
                        tiles[item - 1].isClickable = false
                        tiles[item - 1].setImageResource(concentrationViewModel.fruits[concentrationViewModel.getSecondFruit()]!!.fruit)
                        concentrationViewModel.attemptedMap.remove(item)
                        concentrationViewModel.attemptedMap.put(item, true)
                        concentrationViewModel.checkMatch()
                        scoreText.setText("Score: " + concentrationViewModel.getScore())

                    }
                }
            }
        }
    }
    fun recoverGame(chosenTiles :MutableList<Int>) {
        for(item in chosenTiles){
            if (tiles[item - 1].visibility == View.VISIBLE && tiles[item - 1].isClickable) {
                tiles[item - 1].setOnClickListener {
                    if (!concentrationViewModel.getFirstAttempted()) {
                        concentrationViewModel.setFirstFruit(concentrationViewModel.getChosenFruits()[item]!!)
                        tiles[item - 1].isClickable = false
                        tiles[item - 1].setImageResource(concentrationViewModel.fruits[concentrationViewModel.getFirstFruit()]!!.fruit)
                        concentrationViewModel.attemptedMap.remove(item)
                        concentrationViewModel.attemptedMap.put(item, true)
                        concentrationViewModel.setFirstAttempted(true)
                    } else {
                        concentrationViewModel.setSecondFruit(concentrationViewModel.getChosenFruits()[item]!!)
                        tiles[item - 1].isClickable = false
                        tiles[item - 1].setImageResource(concentrationViewModel.fruits[concentrationViewModel.getSecondFruit()]!!.fruit)
                        concentrationViewModel.attemptedMap.remove(item)
                        concentrationViewModel.attemptedMap.put(item, true)
                        concentrationViewModel.checkMatch()
                        scoreText.setText("Score: " + concentrationViewModel.getScore())

                    }
                }
            }
        }
    }

    fun <K, V> getKey(map: Map<K, V>, target: V): K? {
        for ((key, value) in map)
        {
            if (target == value) {
                return key
            }
        }
        return null
    }
    override fun onResume() {
        super.onResume()
        if (!backgroundMusic.isPlaying) {
            backgroundMusic.prepare()
            backgroundMusic.seekTo(concentrationViewModel.getSongTimeStamp())
            backgroundMusic.start()
            backgroundMusic.isLooping = true
        }

    }

    override fun onPause() {
        super.onPause()
        concentrationViewModel.setSongTimeStamp(backgroundMusic.currentPosition)
        backgroundMusic.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        backgroundMusic.release()
    }
}
