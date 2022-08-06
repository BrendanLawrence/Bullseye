package com.brendanlawrence.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import androidx.appcompat.app.AlertDialog
import com.brendanlawrence.myapplication.databinding.ActivityMainBinding
import kotlin.math.abs
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var sliderValue = 0
    private var targetValue = newTargetValue()
    private var totalScore = 0
    private var round = 1

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // Inflate converts the XML file into the corresponding view object, allowing use in Kotlin code.
        val view = binding.root // Using val instead of var as we will not be changing this 'view' object once created.
        setContentView(view)

        startNewGame()

        binding.hitMeButton.setOnClickListener {
//            Log.i("Button Click Event", "You clicked the Hit Me Button")
            showResult()
            totalScore += pointsForCurrentRound()
            binding.gameScoreTextView?.text = totalScore.toString()
        }

        binding.startOverButton?.setOnClickListener {
            startNewGame()
        }

        binding.targetSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                sliderValue = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }

    private fun differenceAmount() = abs(sliderValue - targetValue)

    private fun newTargetValue () = Random.nextInt(1, 100);

    private fun pointsForCurrentRound(): Int {
        val maxScore: Int = 100
        val difference = differenceAmount()
        var points = maxScore - difference

        if (difference == 0) {
            points += 100
        } else if (difference == 1)
            points += 50

//        var difference: Int = if (sliderValue > targetValue) {
//            sliderValue - targetValue
//        } else if (sliderValue < targetValue) {
//            targetValue - sliderValue
//        } else {
//            0
//        }


//        var difference = sliderValue - targetValue;
//        if (difference < 0)
//            difference *= -1
//
//        return maxScore - difference;

        return points
    }

    private fun startNewGame() {
        totalScore = 0
        round = 1
        sliderValue = 50
        targetValue = newTargetValue()

        binding.gameScoreTextView?.text = totalScore.toString()
        binding.gameRoundTextView?.text = round.toString()
        binding.targetTextView.text = targetValue.toString()
        binding.targetSeekBar.progress = sliderValue
    }

    private fun showResult() {
        val dialogTitle = alertTitle()
        val dialogMessage = getString(R.string.result_dialog_message, sliderValue, pointsForCurrentRound())
//        val dialogMessage = "The slider's value is $sliderValue"

        val builder = AlertDialog.Builder(this)

        builder.setTitle(dialogTitle)
        builder.setMessage(dialogMessage)
        builder.setPositiveButton(R.string.result_dialog_button_text) { dialog, _ ->
            round++
            binding.gameRoundTextView?.text = round.toString()

            targetValue = newTargetValue()
            binding.targetTextView.text = targetValue.toString()
            dialog.dismiss()
        }

        builder.create().show()
    }

    private fun alertTitle(): String {
        val difference = differenceAmount()

        val title: String = when {
            difference == 0 -> {
                getString(R.string.alert_title_1)
            }
            difference < 5 -> {
                getString(R.string.alert_title_2)
            }
            difference <= 10 -> {
                getString(R.string.alert_title_3)
            }
            else -> {
                getString(R.string.alert_title_4)
            }
        }

        return title;
    }
}