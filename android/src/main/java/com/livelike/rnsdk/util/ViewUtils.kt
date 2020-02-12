package com.livelike.rnsdk.util

import android.content.Context

object ViewUtils {

    fun dpToPx(context: Context, dp: Int): Int {
        val density = context.resources.displayMetrics.density
        return Math.round(dp.toFloat() * density)
    }

    fun pxToDp(context: Context, px: Int): Int {
        val density = context.resources.displayMetrics.density
        return Math.round(px.toFloat() / density)
    }
}