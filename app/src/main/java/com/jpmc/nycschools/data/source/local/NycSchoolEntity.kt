package com.jpmc.nycschools.data.source.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jpmc.nycschools.data.repository.NycSchool

@Entity(tableName = "nyc_schools_table")
data class NycSchoolEntity(
    // As needed, all the other fields from API can also be added here
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "school_id")
    val dbn: String,
    val school_name: String,
    val boro: String? = "",
    val overview_paragraph: String? = "",
    val location: String? = "",
    val phone_number: String? = "",
    val website: String? = "",
    val school_email: String? = ""
) {
    fun toNycSchool(): NycSchool {
        return NycSchool(
            schoolId = dbn,
            schoolName = school_name,
            schoolOverview = overview_paragraph,
            schoolEmail = school_email,
            schoolLocation = location,
            schoolPhoneNumber = phone_number,
            schoolWebsite = website
        )
    }
}

fun NycSchool.toNycSchoolEntity(): NycSchoolEntity {
    return NycSchoolEntity(
        dbn = schoolId,
        school_name = schoolName,
        overview_paragraph = schoolOverview,
        school_email = schoolEmail,
        location = schoolLocation,
        website = schoolWebsite,
        phone_number = schoolPhoneNumber
    )
}
