package com.kou.fisaa.data.local.flightLocalManager

import androidx.room.*
import com.kou.fisaa.data.entities.Flight


@Dao
interface FlightDao {

    @Query("SELECT * FROM  flight order by departure") //order by "colomun" DESC
    fun getAll(): List<Flight>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(flights: List<Flight>)

    @Delete
    fun deleteAll(flights: List<Flight>)
}