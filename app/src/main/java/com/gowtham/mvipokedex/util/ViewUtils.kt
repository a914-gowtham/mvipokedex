package com.gowtham.mvipokedex.util

import android.view.View
import android.view.ViewTreeObserver

fun View.gone(){
    this.visibility=View.GONE
}

fun View.show(){
    this.visibility=View.VISIBLE
}

fun <T : View> T.height(function: (Int) -> Unit) {
    if (height == 0)
        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                function(height)
            }
        })
    else function(height)
}

//Usage
//view.height { Log.i("Info", "Here is your height:" + it) }