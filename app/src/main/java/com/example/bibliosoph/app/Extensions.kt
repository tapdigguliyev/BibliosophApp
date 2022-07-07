package com.example.bibliosoph.app

import android.view.View
import com.example.bibliosoph.R
import java.text.SimpleDateFormat
import java.util.*

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun Date?.dateToString(): String? {
    val format = BibliosophApplication.instance.getString(R.string.date_label)
    val dateFormatter = SimpleDateFormat(format, Locale.UK)
    if (this == null) {
        return null
    }
    return dateFormatter.format(this)
}

fun String?.stringToDate(): Date? {
    val format = BibliosophApplication.instance.getString(R.string.date_label)
    val dateFormatter = SimpleDateFormat(format, Locale.UK)
    if (this.isNullOrEmpty()) {
        return null
    }
    return dateFormatter.parse(this)
}