package com.example.kotlinproject

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.abs

class HardModeActivity : AppCompatActivity() {
    lateinit var textView: TextView
    val range = 1..100
    var randomNumber = range.random()
    lateinit var showAlert:ShowAlert
    lateinit var guess: EditText
    lateinit var checkButton: Button
    lateinit var countDownTimer: CountDownTimer
    lateinit var startButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        var mediaPlayer = MediaPlayer.create(this, R.raw.congrats)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hardmode)
        textView = findViewById(R.id.countDown)
        guess = findViewById(R.id.guess)
        checkButton = findViewById(R.id.valid)
        startButton = findViewById(R.id.start)
        guess.isEnabled = false
        checkButton.isEnabled = false
        showAlert= ShowAlert(context = this)
        guess.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkButton.isEnabled = !s.isNullOrBlank()
            }

            override fun afterTextChanged(s: Editable?) {
                checkButton.isEnabled = !s.isNullOrBlank()
            }
        })
        startButton.setOnClickListener() {
            guess.isEnabled = true
            guess.text.clear()

            countDownTimer = object : CountDownTimer(20000, 1000) {


                override fun onTick(millisUntilFinished: Long) {
                    textView.setText("seconds remaining: " + millisUntilFinished / 1000)
                    startButton.isEnabled = false
                }


                override fun onFinish() {
                    textView.setText("Game over!")
                    guess.isEnabled = false
                    checkButton.isEnabled = false
                    rematch()


                }
            }.start()
        }
        checkButton.setOnClickListener(View.OnClickListener {
            val guessedNumber = guess.text.toString().toInt()
            val checkNumber = abs(randomNumber.toString().toInt() - guessedNumber)
            var alertTitle: String = ""
            var alertContext: String = ""
            when {
                guessedNumber > 100 -> {
                    alertTitle = "Out of Range"
                    alertContext = "the number you entered is bigger than 100"

                }

                guessedNumber < 1 -> {
                    alertTitle = "Out of Range"
                    alertContext = "the number you entered is less than 1"

                }

                guessedNumber == randomNumber.toString().toInt() -> {
                    alertTitle = "Congrats"
                    alertContext =
                        "Congrats you guessed the right number ${randomNumber.toString().toInt()}"
                    countDownTimer.cancel()
                    checkButton.isEnabled = false
                    guess.isEnabled = false
                    rematch()
                    mediaPlayer.start()

                }

                checkNumber in (100 * 3 / 4) until 100 -> {
                    alertTitle = "FAR"
                    alertContext = "Cold"


                }

                checkNumber in (100 / 2) until (100 * 3 / 4) -> {
                    alertTitle = "Less Close"
                    alertContext = "You got a little close "


                }

                checkNumber in (100 / 4) until (100 / 2) -> {
                    alertTitle = "WARM"
                    alertContext = "You got more closer "


                }

                checkNumber in (100 / 8) until (100 / 4) -> {
                    alertTitle = "HOT"
                    alertContext = "Close to the number "


                }

                checkNumber in 10 until (100 / 8) -> {
                    alertTitle = "SO HOT"
                    alertContext = "VERY CLOSE "


                }

                checkNumber in 0 until 10 -> {
                    alertTitle = "SOOO HOT"
                    alertContext = "It's in here "


                }
            }
            showAlert.showAlertDialog(alertTitle, alertContext)


        })
    }

    fun rematch() {
        startButton.text = "Rematch ?"
        startButton.isEnabled = true
        randomNumber = range.random()
    }
}