package com.example.kotlinproject

import android.media.MediaPlayer
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ListAdapter
import kotlin.math.abs


class EasyModeActivity : AppCompatActivity() {
    var listItems= ArrayList<String>()
    lateinit var listView:ListView
    lateinit var listAdapter: ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.easymode)
        val guess=findViewById<EditText>(R.id.guess)
        val range =1..100
        val randomNumber = range.random()
        val checkButton:Button=findViewById(R.id.valid)
        val showAlert= ShowAlert(context=this)
        listView=findViewById(R.id.historic)
        listAdapter=ArrayAdapter(this,android.R.layout.simple_list_item_1,listItems)
        listView.adapter=listAdapter
        checkButton.isEnabled=false
        var mediaPlayer = MediaPlayer.create(this, R.raw.congrats)

        guess.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkButton.isEnabled=!s.isNullOrBlank()
            }

            override fun afterTextChanged(s: Editable?) {
                    checkButton.isEnabled=!s.isNullOrBlank()
            }
        })
        checkButton.setOnClickListener(View.OnClickListener {
            val guessedNumber=guess.text.toString().toInt()
            val checkNumber= abs(randomNumber.toString().toInt()-guessedNumber)
            var alertTitle :String=""
            var alertContext:String=""
            when {
                guessedNumber > 100 -> {
                    alertTitle="Out of Range"
                    alertContext="the number you entered is bigger than 100"

                }
                guessedNumber < 1 -> {
                    alertTitle="Out of Range"
                    alertContext="the number you entered is less than 1"

                }
                guessedNumber == randomNumber.toString().toInt() -> {
                    alertTitle="Congrats"
                    alertContext="Congrats you guessed the right number ${randomNumber.toString().toInt()}"
                    checkButton.isEnabled = false
                    guess.isEnabled = false
                    mediaPlayer.start()

                }
                checkNumber in (100 * 3 / 4) until 100 -> {
                    alertTitle="FAR"
                    alertContext="Cold"



                }
                checkNumber in (100 / 2) until (100 * 3 / 4) -> {
                    alertTitle="Less Close"
                    alertContext="You got a little close "



                }
                checkNumber in (100 / 4) until (100 / 2) -> {
                    alertTitle="WARM"
                    alertContext="You got more closer "



                }
                checkNumber in (100 / 8) until (100 / 4) -> {
                    alertTitle="HOT"
                    alertContext="Close to the number "



                }
                checkNumber in 10 until (100 / 8) -> {
                    alertTitle="SO HOT"
                    alertContext="VERY CLOSE "



                }
                checkNumber in 0 until 10 -> {
                    alertTitle="SOOO HOT"
                    alertContext="It's in here "



                }
            }
            showAlert.showAlertDialog(alertTitle, alertContext)
            if(guessedNumber in 1..100 && guessedNumber != randomNumber)
            {
                addToAdapter(guessedNumber.toString())
            }


        })



    }
    private fun addToAdapter(numberToAdd:String){
        listItems.add(numberToAdd)
        if(listItems.size>10)
        {
            listItems.removeAt(0)
        }
        listAdapter.notifyDataSetChanged()
    }

}