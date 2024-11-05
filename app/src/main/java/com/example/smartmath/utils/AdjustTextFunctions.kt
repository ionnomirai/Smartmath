package com.example.smartmath.utils

import android.text.SpannableString
import android.text.Spanned
import android.text.style.UnderlineSpan

fun getUnderlinedText(text: String): SpannableString{
    val spannable = SpannableString(text)
    spannable.setSpan(UnderlineSpan(), 0, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    return spannable
}