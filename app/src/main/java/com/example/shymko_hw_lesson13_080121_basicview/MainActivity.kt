package com.example.shymko_hw_lesson13_080121_basicview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.example.shymko_hw_lesson13_080121_basicview.databinding.ActivityMainBinding

/*
Layout у якому є 3 Views: TextView, ProgressBar (Circle) і Button.
TextView має значення n = 0.
ProgressBar - не видимий і знаходиться по центру layout
При кліку на кнопку Button: TextView і Button зникають, а ProgressBar з’являється на (n+1)/10 секунд.
Після чого видимість об`єктів повертається на попередню.
І значення TextView (n) збільшується на 1.
 */

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    companion object {
        var tvCounter: Long = 0
        var startTriger: Boolean = false
        internal var status = 0
        private val handler = Handler()
        private lateinit var binding: ActivityMainBinding
    }

    var tempBundle: Bundle = Bundle();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupBinding()
        setupOnClickListener()
        progresBar()
    }

    override fun onStart() {
        super.onStart()
        Log.d("OnStart", "startTriger $startTriger")
        setupOnClickListener()
        elementsDisappearing(startTriger)
        incrementionTextView(startTriger)
        timeCalculation(startTriger)
        Log.d("OnStart", "startTriger $startTriger")
    }

    private fun setupBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun progresBar() {
        val resources = resources
        val drawable = resources.getDrawable(R.drawable.circular_progress_bar)
        val progressBar: ProgressBar = findViewById(R.id.circularProgressbar)
        progressBar.progress = 0
        progressBar.secondaryProgress = 100
        progressBar.max = 100
        progressBar.progressDrawable = drawable
        var textView = findViewById<View>(R.id.tvTextView)
        Thread {
            while (status < 100) {
                status += 1
                handler.post {
                    progressBar.progress = status
                    //textView.text = String.format("%d%%", status)
                }
                try {
                    Thread.sleep(160)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                if (status == 99) {
                    status = 0
                }
            }
        }.start()
    }

    private fun setupOnClickListener() {
        binding.btnButton.setOnClickListener {
            startTriger = true
            onStart()
        }
    }

    fun timeCalculation(triger: Boolean) {
        if (triger) {
            var timeOfHid: Long = ((tvCounter + 1) / 10) * 1000
            object : CountDownTimer(timeOfHid, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                }

                override fun onFinish() {
                    startTriger = false
                    Log.d("onFinish", "startTriger $startTriger")
                    onStart()
                }
            }.start()
        }
    }

    fun elementsDisappearing(trigerHide: Boolean) {
        val progressBar: ProgressBar = findViewById(R.id.circularProgressbar)
        if (trigerHide) {
            binding.btnButton.setVisibility(View.INVISIBLE)
            binding.tvTextView.setVisibility(View.INVISIBLE)
            progressBar.setVisibility(View.VISIBLE)
        } else {
            binding.btnButton.setVisibility(View.VISIBLE)
            binding.tvTextView.setVisibility(View.VISIBLE)
            progressBar.setVisibility(View.INVISIBLE)
        }
    }

    private fun incrementionTextView(triger: Boolean) {
        if (triger) {
            tvCounter++
            binding.tvTextView.text = "n= " + tvCounter.toString()
        }
    }
}




