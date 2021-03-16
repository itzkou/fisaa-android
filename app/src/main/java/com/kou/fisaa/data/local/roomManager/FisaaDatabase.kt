package com.kou.fisaa.data.local.roomManager

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kou.fisaa.data.entities.User
import com.kou.fisaa.data.local.authLocalManager.FisaaDao

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class FisaaDatabase : RoomDatabase() {

    abstract fun fisaaDao(): FisaaDao


    companion object {
        @Volatile private var instance: FisaaDatabase? = null

        fun getDatabase(context: Context): FisaaDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, FisaaDatabase::class.java, "fisaa")
                .fallbackToDestructiveMigration()
                .build()
    }

}