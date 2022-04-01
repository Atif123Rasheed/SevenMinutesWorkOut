package com.example.mysevenminutesworkout

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import com.example.mysevenminutesworkout.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {
    private var binding:ActivityExerciseBinding?= null

    private var mExerciseList: ArrayList<ExerciseModels>? = null
    private var mCurrentExercisePosition = -1
    // private var mSelectedExerciseModels: Int = 0

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0

    private var exerciseTimer: CountDownTimer? = null

    private var exerciseProgress = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExercise)

        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        mExerciseList = Constants.defaultExerciseList()
        binding?.toolbarExercise?.setNavigationOnClickListener{
            onBackPressed()
        }


        setUpRestView()
    }

    private fun setUpRestView(){
        binding?.frameLayoutRestProgressBar?.visibility = VISIBLE
        binding?.tvTitle?.visibility= VISIBLE
        binding?.tvExerciseName?.visibility= INVISIBLE
        binding?.frameLayoutExercise?.visibility = INVISIBLE
        binding?.ivExercise?.visibility= INVISIBLE
        binding?.tvUpcomingExerciseLable?.visibility= VISIBLE
        binding?.tvUpcomingExerciseName?.visibility= VISIBLE

        if (restTimer!=null){
            restTimer?.cancel()
            restProgress = 0
        }

        binding?.tvUpcomingExerciseName?.text = (mExerciseList!![mCurrentExercisePosition+1].getExerciseName())
        setRestProgressBar()
    }

    private fun setUpExerciseView(){
        binding?.frameLayoutRestProgressBar?.visibility = INVISIBLE
        binding?.tvTitle?.visibility= INVISIBLE
        binding?.tvExerciseName?.visibility= VISIBLE
        binding?.ivExercise?.visibility= VISIBLE
        binding?.frameLayoutExercise?.visibility = VISIBLE
        binding?.tvUpcomingExerciseLable?.visibility= INVISIBLE
        binding?.tvUpcomingExerciseName?.visibility= INVISIBLE


        if(exerciseTimer!=null){
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }
        setExerciseProgressBar()

        binding?.ivExercise?.setImageResource(mExerciseList!![mCurrentExercisePosition].getExerciseImage())
        binding?.tvExerciseName?.text = (mExerciseList!![mCurrentExercisePosition].getExerciseName())
    }

    private fun setRestProgressBar(){
        binding?.progressBar?.progress = restProgress
        restTimer = object : CountDownTimer(1000, 1000){
            override fun onTick(p0: Long) {
                restProgress++
                binding?.progressBar?.progress = 10 - restProgress
                binding?.tvTimer?.text = (10-restProgress).toString()
            }
            override fun onFinish() {
                mCurrentExercisePosition++
                setUpExerciseView()
            }
        }.start()

    }

    private fun setExerciseProgressBar(){
        binding?.exerciseProgressBar?.progress = exerciseProgress
        exerciseTimer = object : CountDownTimer(3000, 1000){
            override fun onTick(p0: Long) {
                exerciseProgress++
                binding?.exerciseProgressBar?.progress = 30 - exerciseProgress
                binding?.tvExerciseTimer?.text = (30-exerciseProgress).toString()
            }
            override fun onFinish() {
                if(mCurrentExercisePosition <mExerciseList?.size!! - 1){
                    setUpRestView()
                }
                else{
                    Toast.makeText(this@ExerciseActivity,
                        "Exercise completed",
                        Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }.start()

    }

    override fun onDestroy(){

        super.onDestroy()
        if (restTimer!=null){
            restTimer?.cancel()
            restProgress = 0
        }
        if(exerciseTimer!=null){
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }
        binding = null
    }
}