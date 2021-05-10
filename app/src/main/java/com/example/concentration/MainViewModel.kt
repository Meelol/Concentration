package com.example.concentration

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private var numOfTiles :Int = 0
    private var timeStamp : Int = 0

    fun setTimeStamp(timeStamp: Int) {
        this.timeStamp = timeStamp
    }
    fun getTimeStamp() :Int{
        return timeStamp
    }

    fun setAmountOfTiles(num: Int) {
        numOfTiles = num
    }
    fun getAmountOfTiles() :Int{
        return numOfTiles
    }
}