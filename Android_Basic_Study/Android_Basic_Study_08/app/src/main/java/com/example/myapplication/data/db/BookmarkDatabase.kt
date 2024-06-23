package com.example.myapplication.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BookmarkEntity::class], version = 1, exportSchema = false)
abstract class BookmarkDatabase : RoomDatabase() {
    abstract fun getBookmarkDAO() : BookmarkDAO
    companion object {
        @Volatile
        private var INSTANCE : BookmarkDatabase? = null
        private fun buildDatabase(context: Context): BookmarkDatabase =
            Room.databaseBuilder(
                context.applicationContext,
                BookmarkDatabase::class.java,
                "bookmark-photos"
            ).build()

        fun getInstance(context: Context): BookmarkDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
    }
}