package com.mario.persistence

import com.mario.persistence.room.StringListTypeConverter
import org.junit.Assert.assertEquals
import org.junit.Test

class ConvertersUnitTest {

    private val string = "[\"Android\",\"Kotlin\",\"Moshi\"]"
    private val stringList = listOf("Android", "Kotlin", "Moshi")

    @Test
    fun stringListToString() {
        assertEquals(string, StringListTypeConverter().stringListToString(stringList))
    }

    @Test
    fun stringToStringList() {
        assertEquals(stringList, StringListTypeConverter().stringToStringList(string))
    }
}
