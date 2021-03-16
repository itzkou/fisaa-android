package com.kou.fisaa.data.local.authLocalManager

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kou.fisaa.data.entities.User


@Dao
interface FisaaDao {

    /** Insert User **/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertUser(user: User)

    /** Get User **/
    @Query("SELECT * FROM users WHERE _id = :id")  // Doesn't support suspend
     fun getUser(id: Int): User

 }