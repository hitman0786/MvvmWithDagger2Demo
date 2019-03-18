package com.example.epaylater

import com.example.epaylater.utlis.DateConverter
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertFalse
import org.junit.Before
import org.junit.Test

class DateConverterTest {

    @Test
    fun testDateIsEmpty() {
        assertEquals(DateConverter.getDate(""), "")
    }

    @Test
    fun testDateIsNotEmpty(){
        assertEquals(DateConverter.getDate("2016-12-11T12:23:34Z"), "Dec 11, 2016")
    }

    @Test
    fun testgetDateFormatForspendIsEmpty() {
        assertEquals(DateConverter.getDateFormatForspend(0), "")
    }

}