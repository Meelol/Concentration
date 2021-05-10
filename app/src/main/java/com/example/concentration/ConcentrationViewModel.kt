package com.example.concentration

import androidx.lifecycle.ViewModel

class ConcentrationViewModel : ViewModel() {

    private var chosenFruits :MutableMap<Int, Int> = mutableMapOf()
    var attemptedMap :MutableMap<Int, Boolean> = mutableMapOf(1 to false, 2 to false, 3 to false, 4 to false, 5 to false,
                                                              6 to false, 7 to false, 8 to false, 9 to false, 10 to false,
                                                              11 to false, 12 to false, 13 to false, 14 to false, 15 to false,
                                                              16 to false, 17 to false, 18 to false, 19 to false, 20 to false)
    private var keys :MutableList<Int> = mutableListOf()
    private var selectedTiles :MutableList<Int> = mutableListOf()
    private var score = 0
    private var tilesMatch = false
    var firstAttemptedTile = false
    var firstFruitRes = 0
    var secondFruitRes = 0

    private var numOfTiles :Int = 0
    private var songTimeStamp :Int = 0

    val fruits = mapOf<Int, Fruit>(
            1 to Fruit(R.drawable.apple, false),
            2 to Fruit(R.drawable.banana, false),
            3 to Fruit(R.drawable.citrus, false),
            4 to Fruit(R.drawable.coconut, false),
            5 to Fruit(R.drawable.grapes, false),
            6 to Fruit(R.drawable.mango, false),
            7 to Fruit(R.drawable.pineapple, false),
            8 to Fruit(R.drawable.kiwi, false),
            9 to Fruit(R.drawable.watermelon, false),
            10 to Fruit(R.drawable.orange, false)
    )
    fun getFruitImage(key :Int) :Int{
        return fruits[key]!!.fruit
    }
    fun setSongTimeStamp(timeStamp :Int)
    {
        songTimeStamp = timeStamp
    }
    fun getSongTimeStamp() :Int{
        return songTimeStamp
    }
    fun setNumOfTiles(num :Int)
    {
        numOfTiles = num
    }
    fun getNumOfTiles() :Int{
        return numOfTiles
    }
    fun checkMatch() {
        if(firstFruitRes == secondFruitRes){
            firstAttemptedTile = false
            increaseScore()
        }
        else{
            decreaseScore()
            firstAttemptedTile = false
        }

    }
    private fun increaseScore(){
        score = score + 2
    }
    private fun decreaseScore(){
        if(score > 0)
        score--
    }
    fun setMatch(bool :Boolean){
        tilesMatch = bool
    }
    fun getMatch() :Boolean{
        return tilesMatch
    }
    fun setFirstAttempted(bool :Boolean)
    {
        firstAttemptedTile = bool
    }
    fun getFirstAttempted() : Boolean{
        return firstAttemptedTile
    }
    fun setFirstFruit(res :Int)
    {
        firstFruitRes = res
    }
    fun getFirstFruit() :Int{
        return firstFruitRes
    }
    fun setSecondFruit(res :Int)
    {
        secondFruitRes = res
    }
    fun getSecondFruit() :Int{
        return secondFruitRes
    }
    fun setKeys(keys :MutableList<Int>){
        this.keys = keys
    }
    fun getKeys() : MutableList<Int>{
        return keys
    }
    fun setChosenTiles(tiles :MutableList<Int>){
        this.selectedTiles = tiles
    }
    fun getChosenTiles() :MutableList<Int>{
        return selectedTiles
    }
    fun setChosenFruits(chosenFruits: MutableMap<Int, Int>){
        this.chosenFruits = chosenFruits
    }
    fun getChosenFruits() :MutableMap<Int,Int>{
        return chosenFruits
    }
    fun setScore(score :Int){
        this.score = score
    }
    fun getScore() :Int{
        return score
    }
    fun setToAttempted(){

    }



}
