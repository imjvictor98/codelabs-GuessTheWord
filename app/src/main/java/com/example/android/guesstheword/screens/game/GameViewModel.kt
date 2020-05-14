package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log.i
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    private lateinit var wordList: MutableList<String>

    private val timer: CountDownTimer

    private val _word = MutableLiveData<String>()
    private val _score = MutableLiveData<Int>()
    private val _eventGameFinish = MutableLiveData<Boolean>()
    private val _currentTime = MutableLiveData<Long>()

    val currentTimeString = Transformations.map(currentTime) { time ->
        DateUtils.formatElapsedTime(time)
    }

    val word: LiveData<String> get() = _word
    val score: LiveData<Int> get() = _score
    val eventGameFinish: LiveData<Boolean> get() = _eventGameFinish
    val currentTime: LiveData<Long> get() = _currentTime



    init {
        i("GameViewModel", "GameViewModel created!")

        _word.value = ""
        _score.value = 0

        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
            override fun onFinish() {
                _currentTime.value = DONE
                onGameFinish()
            }

            override fun onTick(p0: Long) { //called in every interval
                _currentTime.value = p0 / ONE_SECOND
                //is the amount of time until the timer is finished in milliseconds
            }

        }

        resetList()
        nextWord()
    }


    override fun onCleared() {
        super.onCleared()

        timer.cancel() //avoid memory leaking
    }

    private fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
        wordList.shuffle()
    }

    private fun nextWord() {
        if (wordList.isEmpty()) {
            resetList() //now it reset until timer over
        } else {
            _word.value = wordList.removeAt(0)
        }
    }

    fun onSkip() {
        _score.value = (score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _score.value = (score.value)?.plus(1)
        nextWord()
    }

    fun onGameFinish() {
        _eventGameFinish.value = true
    }

    fun onGameFinishComplete() {
        _eventGameFinish.value = false
    }

    companion object {
        private const val DONE = 0L //for game is over

        private const val ONE_SECOND = 1000L //time interval

        private const val COUNTDOWN_TIME = 60000L //total time

    }
}