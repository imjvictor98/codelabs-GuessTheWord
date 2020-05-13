package com.example.android.guesstheword.screens.score

import android.util.Log.i
import androidx.lifecycle.ViewModel

class ScoreViewModel(finalScore: Int): ViewModel() {

    var score = finalScore

    init {
        i("ScoreViewModel", "Final score is $finalScore")
    }
}