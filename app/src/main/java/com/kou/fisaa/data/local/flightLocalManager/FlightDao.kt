package com.kou.fisaa.data.local.flightLocalManager

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kou.fisaa.data.entities.Flight


@Dao
interface FlightDao {

    @Query("SELECT * FROM  flight ") //order by "colomun" DESC
    fun getAll(): List<Flight>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(flights: List<Flight>)

    @Query("DELETE FROM flight")
    fun deleteAll()
}