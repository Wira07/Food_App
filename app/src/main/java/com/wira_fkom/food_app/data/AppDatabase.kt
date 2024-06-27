package com.wira_fkom.food_app.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Profile::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
}
