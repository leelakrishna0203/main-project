package com.example.bustract

import android.app.Activity
import android.text.Spanned
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment

fun Activity.showToast(message: Any?) {
    Toast.makeText(this, "$message", Toast.LENGTH_SHORT).show()
}

fun Fragment.showToast(message: Any?) {
    Toast.makeText(context, "$message", Toast.LENGTH_SHORT).show()
}

fun Activity.spannaAble(string: String): Spanned {
    return HtmlCompat.fromHtml(string, HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS)
}