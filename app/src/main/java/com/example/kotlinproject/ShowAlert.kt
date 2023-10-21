package com.example.kotlinproject

import android.content.Context
import androidx.appcompat.app.AlertDialog

class ShowAlert(context: Context) {
    private val alertDialog:AlertDialog=AlertDialog.Builder(context).create()
    fun showAlertDialog(title:String,message:String)
    {
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,"OK"){
            _,_->
        }
    alertDialog.show()

    }

}