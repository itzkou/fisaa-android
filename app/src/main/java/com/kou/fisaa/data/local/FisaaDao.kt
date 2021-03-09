package com.kou.fisaa.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kou.fisaa.data.entities.User


@Dao
interface FisaaDao {

    /** Insert User **/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    /** Get User **/
    @Query("SELECT * FROM users WHERE _id = :id")  // Doesn't support suspend
     fun getUser(id: Int): LiveData<User>

 }