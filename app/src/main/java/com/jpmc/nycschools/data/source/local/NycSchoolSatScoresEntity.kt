package com.jpmc.nycschools.data.source.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jpmc.nycschools.data.repository.NycSchoolSatScores

@Entity(tableName = "nyc_schools_sat_scores_table")
data class NycSchoolSatScoresEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "school_id")
    val dbn: String,
    val school_name: String = "",
    val num_of_sat_test_takers: String = "",
    val sat_critical_reading_avg_score: String = "",
    val sat_math_avg_score: String = "",
    val sat_writing_avg_score: String = ""
) {
    fun toNycSchoolSatScores(): NycSchoolSatScores {
        return NycSchoolSatScores(
            schoolId = dbn,
            schoolName = school_name,
            numOfSatTestTakers = num_of_sat_test_takers,
            satCriticalReadingAvgScore = sat_critical_reading_avg_score,
            satMathAvgScore = sat_math_avg_score,
            satWritingAvgScore = sat_writing_avg_score
        )
    }
}
