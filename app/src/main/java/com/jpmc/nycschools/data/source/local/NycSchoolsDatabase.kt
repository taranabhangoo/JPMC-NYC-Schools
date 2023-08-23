package com.jpmc.nycschools.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jpmc.nycschools.data.repository.NycSchool

@Database(entities = [NycSchoolEntity::class, NycSchoolSatScoresEntity::class], version = 1)
abstract class NycSchoolsDatabase : RoomDatabase() {

    abstract val nycSchoolsDao: NycSchoolsDao
    abstract val nycSchoolsSatScoresDao: NycSchoolsSatScoresDao

    companion object {

        @Volatile
        private var INSTANCE: NycSchoolsDatabase? = null

        fun getInstance(context: Context): NycSchoolsDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NycSchoolsDatabase::class.java,
                        "nyc_schools_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}