package com.example.mysevenminutesworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysevenminutesworkout.databinding.ActivityExerciseBinding
import com.example.mysevenminutesworkout.databinding.DialogueOnbackPressButtonBinding
import java.util.*

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding: ActivityExerciseBinding? = null

    private var mExerciseList: ArrayList<ExerciseModels>? = null
    private var mCurrentExercisePosition = -1
    // private var mSelectedExerciseModels: Int = 0

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0

    private var exerciseTimer: CountDownTimer? = null
    private var tts: TextToSpeech? = null

    private var player: MediaPlayer? = null
    private var exerciseAdapter: ExerciseStatusAdapter? = null

    private var exerciseProgress = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        tts = TextToSpeech(this, this)
        mExerciseList = Constants.defaultExerciseList()

        initView()
        setSupportActionBar()
        setUpExerciseStatusRecyclerView()
        setUpRestView()
    }

    private fun initView() {
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }

    private fun setSupportActionBar() {
        setSupportActionBar(binding?.toolbarExercise)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarExercise?.setNavigationOnClickListener {
            backDialogueButtonPress()
        }
    }

    private fun backDialogueButtonPress() {
        val customDialogue = Dialog(this)
        val customDialogueBinding = DialogueOnbackPressButtonBinding.inflate(layoutInflater)
        customDialogue.setContentView(customDialogueBinding.root)
        customDialogue.setCanceledOnTouchOutside(false)
        customDialogueBinding.btnYes.setOnClickListener {
            this@ExerciseActivity.finish()
            customDialogue.dismiss()
        }
        customDialogueBinding.btnNo.setOnClickListener {
            customDialogue.dismiss()
        }
        customDialogue.show()
    }

    override fun onBackPressed() {
        backDialogueButtonPress()
    }


    private fun setUpExerciseStatusRecyclerView() {

        binding?.rvExerciseStatus?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        exerciseAdapter = ExerciseStatusAdapter(mExerciseList!!)
        binding?.rvExerciseStatus?.adapter = exerciseAdapter
    }

    private fun setUpRestView() {

        try {
            val soundURI =
                Uri.parse("android.resource://com.example.mysevenminutesworkout/" + R.raw.let_start)
            player = MediaPlayer.create(applicationContext, soundURI)
            player?.isLooping = false
            player?.start()

        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding?.frameLayoutRestProgressBar?.visibility = VISIBLE
        binding?.tvTitle?.visibility = VISIBLE
        binding?.tvExerciseName?.visibility = INVISIBLE
        binding?.frameLayoutExercise?.visibility = INVISIBLE
        binding?.ivExercise?.visibility = INVISIBLE
        binding?.tvUpcomingExerciseLable?.visibility = VISIBLE
        binding?.tvUpcomingExerciseName?.visibility = VISIBLE

        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }

        binding?.tvUpcomingExerciseName?.text =
            (mExerciseList!![mCurrentExercisePosition + 1].getExerciseName())
        setRestProgressBar()
    }

    private fun setUpExerciseView() {
        binding?.frameLayoutRestProgressBar?.visibility = INVISIBLE
        binding?.tvTitle?.visibility = INVISIBLE
        binding?.tvExerciseName?.visibility = VISIBLE
        binding?.ivExercise?.visibility = VISIBLE
        binding?.frameLayoutExercise?.visibility = VISIBLE
        binding?.tvUpcomingExerciseLable?.visibility = INVISIBLE
        binding?.tvUpcomingExerciseName?.visibility = INVISIBLE


        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }
        speakText(mExerciseList!![mCurrentExercisePosition].getExerciseName())
        setExerciseProgressBar()

        binding?.ivExercise?.setImageResource(mExerciseList!![mCurrentExercisePosition].getExerciseImage())
        binding?.tvExerciseName?.text =
            (mExerciseList!![mCurrentExercisePosition].getExerciseName())
    }

    private fun setRestProgressBar() {
        binding?.progressBar?.progress = restProgress
        restTimer = object : CountDownTimer(2000, 2000) {
            override fun onTick(p0: Long) {
                restProgress++
                binding?.progressBar?.progress = 10 - restProgress
                binding?.tvTimer?.text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                mCurrentExercisePosition++
                mExerciseList!![mCurrentExercisePosition].setIsExerciseSelected(true)
                exerciseAdapter?.notifyDataSetChanged()
                setUpExerciseView()
            }
        }.start()

    }

    private fun setExerciseProgressBar() {
        binding?.exerciseProgressBar?.progress = exerciseProgress
        exerciseTimer = object : CountDownTimer(2000, 2000) {
            override fun onTick(p0: Long) {
                exerciseProgress++
                binding?.exerciseProgressBar?.progress = 30 - exerciseProgress
                binding?.tvExerciseTimer?.text = (30 - exerciseProgress).toString()
            }

            override fun onFinish() {


                if (mCurrentExercisePosition < mExerciseList?.size!! - 1) {
                    mExerciseList!![mCurrentExercisePosition].setIsExerciseSelected(false)
                    mExerciseList!![mCurrentExercisePosition].setIsExerciseCompleted(true)
                    exerciseAdapter?.notifyDataSetChanged()
                    setUpRestView()
                } else {
                    finish()
                    val intent = Intent(this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(intent)
                }
            }
        }.start()

    }

    override fun onDestroy() {

        super.onDestroy()
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        if (tts != null) {
            tts?.stop()
            tts?.shutdown()
        }
        if (player != null) {
            player?.stop()

        }
        binding = null
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            var result = tts!!.setLanguage(Locale.UK)

            if (result == TextToSpeech.LANG_MISSING_DATA
                || result == TextToSpeech.LANG_NOT_SUPPORTED
            ) {
                Log.e("tts", "The Language Specified not Supported")
            }

        } else {
            Log.e("tts", "Initialization Failed")
        }
    }

    private fun speakText(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, " ")

    }
}