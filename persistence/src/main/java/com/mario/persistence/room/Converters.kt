package com.mario.persistence.room

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

/**
 * Created by mariolopez on 23/1/18.
 */
class StringListTypeConverter {
    private val adapter: JsonAdapter<List<String>>
        get() {
            val moshi = Moshi.Builder().build()
            val listType = Types.newParameterizedType(List::class.java, String::class.java)
            return moshi.adapter(listType)
        }

    @TypeConverter
    fun stringListToString(strings: List<String>): String = adapter.toJson(strings)


    @TypeConverter
    fun stringToStringList(string: String): List<String> = adapter.fromJson(string) ?: emptyList()
}
