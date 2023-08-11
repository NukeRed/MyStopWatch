package com.derkun.mystopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import com.derkun.mystopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var running = false
    var offset: Long = 0


    val OFFSET_KEY = "offset"
    val RUNNING_KEY = "running"
    val BASE_KEY = "base"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Restore the previous state
        if (savedInstanceState != null){
            offset = savedInstanceState.getLong(OFFSET_KEY)
            running = savedInstanceState.getBoolean(RUNNING_KEY)

            if (running){
                binding.stopwatch.base = savedInstanceState.getLong(BASE_KEY)
                binding.stopwatch.start()
            } else setBaseTime()
        }

        binding.startButton.setOnClickListener {
                if (!running) {
                    setBaseTime()
                    binding.stopwatch.start()
                    running = true
                }
        }
       binding.pauseButton.setOnClickListener {
            if (running){
                saveOffset()
                binding.stopwatch.stop()
                running = false
            }
        }




        // The reset button sets the offset and stopwatch to 0
        val resetButton = findViewById<Button>(R.id.reset_button)
        resetButton.setOnClickListener {
            offset = 0
            setBaseTime()
        }
    }


    override fun onStart(){
        super.onStart()
        // It is place where code to run when the activity starts
    }


    override fun onPause(){
        super.onPause()
        // It is place where code to run when the activity stops
        if(running){
            saveOffset()
            binding.stopwatch.stop()
        }
    }

    override fun onResume(){
        super.onResume()
        if (running){
            setBaseTime()
            binding.stopwatch.start()
            offset = 0
        }
    }
    override fun onSaveInstanceState(savedInstanceState: Bundle){
        savedInstanceState.putLong(OFFSET_KEY,offset)
        savedInstanceState.putBoolean(RUNNING_KEY,running)
        savedInstanceState.putLong(BASE_KEY,binding.stopwatch.base)
        super.onSaveInstanceState(savedInstanceState)
    }



    fun setBaseTime() {
        binding.stopwatch.base = SystemClock.elapsedRealtime() - offset

    }

    fun saveOffset(){
        offset = SystemClock.elapsedRealtime() - binding.stopwatch.base
    }
}