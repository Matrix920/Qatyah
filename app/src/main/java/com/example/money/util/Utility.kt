package com.example.money.util

import android.content.Context
import android.view.Gravity
import android.widget.Toast
import android.content.DialogInterface
import android.R
import androidx.appcompat.app.AlertDialog



class Utility {
    companion object {
        public fun getInt(b: Boolean): Int {
            if (b)
                return 1
            return 0
        }

        public fun getBool(i: Int): Boolean {
            if (i == 1)
                return true
            return false
        }

        public fun viewMessage(context: Context, msg: String) {
            val toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }

        fun rand(start: Int, end: Int): Int {
            require(start <= end) { "Illegal Argument" }
            return (start..end).random()
        }


        public fun filter(list:List<Int>,x:Int):Boolean{
            return list.filter { it == x }.isEmpty()
        }


    }
}