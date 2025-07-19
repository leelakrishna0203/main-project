package com.example.bustract

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable

class Comfun(val context: Context) {
    val p=Dialog(context).apply {
        setContentView(R.layout.dialog)
        setCancelable(false)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}


