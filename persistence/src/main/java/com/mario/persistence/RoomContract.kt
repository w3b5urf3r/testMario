package com.mario.persistence

/**
 * Created by mariolopez on 28/12/17.
 */
class RoomContract {

    companion object {
        const val DATABASE_NUMBERS = "NumberDB.db"

        const val TABLE_NUMBER = "Sample_Model_Table"
        internal const val SELECT_FROM = "SELECT * FROM "

        const val SELECT_NUMBER = SELECT_FROM + TABLE_NUMBER
    }
}