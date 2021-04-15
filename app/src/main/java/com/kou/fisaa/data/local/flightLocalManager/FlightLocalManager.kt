package com.kou.fisaa.data.local.flightLocalManager

import com.kou.fisaa.data.entities.Flight
import javax.inject.Inject

class FlightLocalManager @Inject constructor(private val flightDao: FlightDao) {
    fun getAll(): List<Flight>? {
        return flightDao.getAll()
    }

    fun insertAll(flights: List<Flight>) {
        return flightDao.insertAll(flights)
    }

    fun deleteAll() {
        return flightDao.deleteAll()
    }


}