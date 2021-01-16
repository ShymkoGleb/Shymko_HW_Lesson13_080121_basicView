package com.example.shymko_hw_lesson13_080121_basicview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.example.shymko_hw_lesson13_080121_basicview.databinding.ActivityMainBinding

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    companion object {
        var tvCounter: Int = 0
        var startTriger: Boolean = false
        internal var status = 0
        private val handler = Handler()

    }


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupBinding()
        setupOnClickListener()
        progresBar()
        //incrementionTextView(tvCounter)
    }

    override fun onStart() {
        super.onStart()
        elementsDisappearing(startTriger)
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
                    Thread.sleep(1600)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }.start()
    }

    private fun setupOnClickListener() {
        binding.btnButton.setOnClickListener {
            Toast.makeText(this, "HELLO", Toast.LENGTH_SHORT)
                    .show()
            startTriger = true
            onStart()
        }
    }

    fun timeCalculation(triger: Boolean): Long {
        var a: Long = 100
        return a
    }

    fun elementsDisappearing(triger: Boolean) {
        val progressBar: ProgressBar = findViewById(R.id.circularProgressbar)
        if (triger) {
            binding.btnButton.setVisibility(View.INVISIBLE)
            binding.tvTextView.setVisibility(View.INVISIBLE)
            progressBar.setVisibility(View.VISIBLE)
        } else {
            binding.btnButton.setVisibility(View.VISIBLE)
            binding.tvTextView.setVisibility(View.VISIBLE)
            progressBar.setVisibility(View.INVISIBLE)

        }
    }

    private fun incrementionTextView(conter: Int) {
        binding.tvTextView.text = conter.toString()
    }


}




