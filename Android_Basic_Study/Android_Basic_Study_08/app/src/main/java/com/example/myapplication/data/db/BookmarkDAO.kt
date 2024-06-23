package com.example.myapplication.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookmarkDAO {
    @Query("SELECT * FROM Bookmark")
    fun getBookmarkList(): List<BookmarkEntity>
    @Query("SELECT COUNT(*) FROM Bookmark WHERE id = :id")
    fun checkBookmark(id: String) : Int
    @Insert
    fun addBookmark(bookmark: BookmarkEntity)
    @Query("DELETE FROM Bookmark WHERE id = :id")
    fun deleteBookmark(id: String)
}