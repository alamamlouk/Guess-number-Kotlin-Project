package com.example.kotlinproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    var radioGroup:RadioGroup?=null
    lateinit var radioButton: RadioButton
    private lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var intent: Intent

        radioGroup=findViewById(R.id.radioGroup1)
        button=findViewById(R.id.start)
        button.setOnClickListener{
            var selectedOption:Int=radioGroup!!.checkedRadioButtonId
            radioButton=findViewById(selectedOption)
            if (radioButton.text.equals("Easy mode"))
            {
                 intent=Intent(this,EasyModeActivity::class.java)
                startActivity(intent)
                }
            if(radioButton.text.equals("Hard Mode"))
            {
                 intent=Intent(this,HardModeActivity::class.java)
                startActivity(intent)
            }
        }
    }
}