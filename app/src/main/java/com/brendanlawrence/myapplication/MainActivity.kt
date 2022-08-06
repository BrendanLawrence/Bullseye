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
    private var targetValue = Random.nextInt(1, 100);

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // Inflate converts the XML file into the corresponding view object, allowing use in Kotlin code.
        val view = binding.root // Using val instead of var as we will not be changing this 'view' object once created.
        setContentView(view)

        binding.targetTextView.text = targetValue.toString()

        binding.hitMeButton.setOnClickListener {
            Log.i("Button Click Event", "You clicked the Hit Me Button")
            showResult()
        }

        binding.targetSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                sliderValue = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }

    private fun pointsForCurrentRound(): Int {
        val maxScore: Int = 100
        val difference = abs(sliderValue - targetValue)

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

        return maxScore - difference
    }

    private fun showResult() {
        val dialogTitle = getString(R.string.result_dialog_title)
        val dialogMessage = getString(R.string.result_dialog_message, sliderValue, pointsForCurrentRound())
//        val dialogMessage = "The slider's value is $sliderValue"

        val builder = AlertDialog.Builder(this)

        builder.setTitle(dialogTitle)
        builder.setMessage(dialogMessage)
        builder.setPositiveButton(R.string.result_dialog_button_text) { dialog, _ ->
            dialog.dismiss()
        }

        builder.create().show()
    }
}