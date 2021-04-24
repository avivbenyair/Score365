package com.example.score365test.extentions

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


fun String.formatDate(inputDateFormat: String, outputDateFormat: String): String {
    val inputDate: Date = SimpleDateFormat(inputDateFormat).parse(this)
    val date: DateFormat = SimpleDateFormat(outputDateFormat)
   return date.format(inputDate)
}
