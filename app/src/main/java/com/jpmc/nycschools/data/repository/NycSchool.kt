package com.jpmc.nycschools.data.repository

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nyc_schools_table")
data class NycSchool(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "school_id")
    val schoolId: String,
    val schoolName: String,
    val schoolLocation: String? = "",
    val schoolEmail: String? = "",
    val schoolPhoneNumber: String? = "",
    val schoolWebsite: String? = "",
    val schoolOverview: String? = ""
)
