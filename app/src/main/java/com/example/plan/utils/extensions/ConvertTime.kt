package com.example.plan.utils.extensions

import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*

@RequiresApi(Build.VERSION_CODES.N)
fun Long.toDatetime(pattern: String = "dd MMM yyyy HH:mm"): String? =
    SimpleDateFormat(pattern, Locale.getDefault()).format(this)

@RequiresApi(Build.VERSION_CODES.N)
fun String.toLongDate(pattern: String = "dd MM yyyy HH:mm"): Long =
    SimpleDateFormat(pattern, Locale.getDefault()).parse(this).time