package com.kou.fisaa.data.local.adLocalManager

import androidx.room.*
import com.kou.fisaa.data.entities.Advertisement

@Dao
interface AdDao {

    @Query("SELECT * FROM  advertisement ")
    fun getAll(): List<Advertisement>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(ads: List<Advertisement>)

    @Delete
    fun deleteAll(ads: List<Advertisement>)
}