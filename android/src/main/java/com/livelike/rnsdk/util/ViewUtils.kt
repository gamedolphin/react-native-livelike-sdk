package com.livelike.rnsdk.util

import android.content.Context
import kotlin.math.roundToInt

object ViewUtils {

    fun dpToPx(context: Context, dp: Int): Int {
        val density = context.resources.displayMetrics.density
        return (dp.toFloat() * density).roundToInt()
    }

    fun pxToDp(context: Context, px: Int): Int {
        val density = context.resources.displayMetrics.density
        return (px.toFloat() / density).roundToInt()
    }
}