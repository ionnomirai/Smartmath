package com.example.smartmath.utils

import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan

/* Underlines the entire line */
fun getUnderlinedText(text: String): SpannableString{
    val spannable = SpannableString(text)
    spannable.setSpan(UnderlineSpan(), 0, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    return spannable
}

/* Make the line bold from index Start to index End (all inclusive) */
fun getBoldText(text: String, start:Int, end:Int): SpannableString{
    val spannable = SpannableString(text)
    spannable.setSpan(StyleSpan(Typeface.BOLD), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    return spannable
}