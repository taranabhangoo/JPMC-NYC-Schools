package com.jpmc.nycschools.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NycSchoolsSatScoresDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(schools: List<NycSchoolSatScoresEntity>)

    @Query("SELECT * FROM nyc_schools_sat_scores_table WHERE :id = school_id")
    fun getSchoolSatScoresBySchoolId(id: String): NycSchoolSatScoresEntity
}