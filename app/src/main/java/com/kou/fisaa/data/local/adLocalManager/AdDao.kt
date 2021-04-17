package com.kou.fisaa.data.local.adLocalManager

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kou.fisaa.data.entities.Advertisement

@Dao
interface AdDao {

    @Query("SELECT * FROM  advertisement ")
    fun getAll(): List<Advertisement>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(ads: List<Advertisement>)

    @Query("DELETE  FROM  advertisement ")
    fun deleteAll()
}