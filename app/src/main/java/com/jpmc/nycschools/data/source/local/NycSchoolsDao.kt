package com.jpmc.nycschools.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jpmc.nycschools.data.repository.NycSchool

@Dao
interface NycSchoolsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(schools: List<NycSchoolEntity>)

    @Query("SELECT * FROM nyc_schools_table")
    fun getSchools(): List<NycSchoolEntity>

    @Query("SELECT * FROM nyc_schools_table WHERE :id = school_id")
    fun getSchoolById(id: String): NycSchoolEntity
}