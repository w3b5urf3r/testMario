package com.mario.persistence

import com.mario.persistence.room.NumberDB

object TestUtil {
    fun createSample(id: Long, name: String): NumberDB =
        NumberDB(id, name = name)
}