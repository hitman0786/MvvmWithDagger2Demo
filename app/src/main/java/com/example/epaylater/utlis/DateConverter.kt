package com.example.epaylater.utlis

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DateConverter {

    fun getDate(mDate: String): String{

        if(mDate == "")
            return ""

        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        val date = format.parse(mDate)

        val dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.US)
        return dateFormat.format(date)
    }

    fun getDateFormatForspend(cdate: Long): String{
        if(cdate == 0.toLong())
            return ""
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        val date = Date(cdate)
        return format.format(date)

    }
}