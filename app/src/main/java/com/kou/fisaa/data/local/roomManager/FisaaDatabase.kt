package com.kou.fisaa.data.local.roomManager

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kou.fisaa.data.entities.Advertisement
import com.kou.fisaa.data.entities.Flight
import com.kou.fisaa.data.entities.User
import com.kou.fisaa.data.local.adLocalManager.AdDao
import com.kou.fisaa.data.local.authLocalManager.AuthDao
import com.kou.fisaa.data.local.flightLocalManager.FlightDao

@Database(
    entities = [User::class, Flight::class, Advertisement::class],
    version = 5,
    exportSchema = false
)
abstract class FisaaDatabase : RoomDatabase() {

    abstract fun authDao(): AuthDao
    abstract fun flightDao(): FlightDao
    abstract fun adDao(): AdDao


    companion object {
        @Volatile
        private var instance: FisaaDatabase? = null

        fun getDatabase(context: Context): FisaaDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, FisaaDatabase::class.java, "fisaa")
                .fallbackToDestructiveMigration()
                .build()
    }

}