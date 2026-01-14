package ru.tikodvlp.geoquiz

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import java.lang.Exception

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"
private const val REQUEST_CODE_CHEAT = 0

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var backButton: ImageButton
    private lateinit var cheatButton: Button
    private lateinit var questionTextView: TextView
    private lateinit var apiLevel: TextView

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate called")
        setContentView(R.layout.activity_main)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        backButton = findViewById(R.id.back_button)
        cheatButton = findViewById(R.id.cheat_button)
        questionTextView = findViewById(R.id.question_text_view)
        apiLevel = findViewById(R.id.api_level)

        val buildNumber = Build.VERSION.SDK_INT.toString() // to display
        apiLevel.text = "API level: $buildNumber"          // sdk in textView

        trueButton.setOnClickListener{ view: View ->
            checkAnswer(true)
            trueButton.isClickable = false
            falseButton.isClickable = false
        }
        falseButton.setOnClickListener{ view: View ->
            checkAnswer(false)
            trueButton.isClickable = false
            falseButton.isClickable = false
        }
        nextButton.setOnClickListener{
            quizViewModel.moveToNext()
            updateQuestion()
            trueButton.isClickable = true
            falseButton.isClickable = true
        }
        backButton.setOnClickListener{
            quizViewModel.moveToBack()
            updateQuestion()
            trueButton.isClickable = false
            falseButton.isClickable = false
        }

        var tokens = 3
        cheatButton.setOnClickListener{ view ->
            if (tokens <= 3 && tokens != 0) {
                tokens -= 1
                makeText(this, "Cheats remaining $tokens", LENGTH_SHORT).show()
                val answerIsTrue = quizViewModel.currentQuestionAnswer
                val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    val options = ActivityOptions.makeClipRevealAnimation(view, 0, 0,
                        view.width, view.height)
                    startActivityForResult(intent, REQUEST_CODE_CHEAT, options.toBundle())
            }else {
                    startActivityForResult(intent, REQUEST_CODE_CHEAT)
                }
            } else {
                cheatButton.isEnabled = false
                makeText(this, "No more cheats", LENGTH_SHORT).show()
            }
        }
        updateQuestion()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            quizViewModel.isCheater =
                data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart called")
    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume called")
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause called")
    }
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop called")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy called")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(TAG, "onSaveInstanceState")
        outState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }
    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer

        val messageResId = when {
            quizViewModel.isCheater -> R.string.judgment_toast
            userAnswer == correctAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT,).show()
    }
}