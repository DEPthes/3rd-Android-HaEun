package com.example.android_basic_study_06

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteDAO {
    @Query("SELECT * FROM FavoriteItem")
    fun getFavoriteItemList(): List<FavoriteItem>  // 찜한 아이템 조회
    @Insert
    fun insertItem(itemInfo: FavoriteItem)    // 찜한 아이템 db에 insert
    @Query("DELETE FROM FavoriteItem WHERE id = :id")
    fun deleteItem(id: Int)    // 찜 목록 삭제
}