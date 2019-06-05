package com.mario.persistence.room

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.mario.persistence.RoomContract

@Entity(
    tableName = RoomContract.TABLE_NUMBER,
    indices = [(Index(value = arrayOf("id"), unique = true))]
)
data class NumberDB(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val number: Int
)