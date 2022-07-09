package com.example.notesapp.core

import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar

fun snackbar(view: View, message: String) {
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
}
fun log(string: String){
    Log.d("TAG", string)
}