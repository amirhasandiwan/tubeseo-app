package com.example.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.dao.SeoProjectDao
import com.example.data.entity.SeoProject

@Database(entities = [SeoProject::class], version = 1, exportSchema = false)
abstract class TubeSeoDatabase : RoomDatabase() {
    abstract fun seoProjectDao(): SeoProjectDao

    companion object {
        @Volatile
        private var INSTANCE: TubeSeoDatabase? = null

        fun getDatabase(context: Context): TubeSeoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TubeSeoDatabase::class.java,
                    "tubeseo_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
