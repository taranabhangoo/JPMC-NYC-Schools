package com.jpmc.nycschools.data.source.local


/***
* Local Database is created so the app performs well even in offline mode
 * */
interface LocalDataSource {
    fun getSchools(): List<NycSchoolEntity>
    fun getSchool(id: String): NycSchoolEntity
    fun getSchoolSatScores(id: String): NycSchoolSatScoresEntity?
    fun addSchools(schools: List<NycSchoolEntity>)
    fun addSchoolSatScores(schoolSatScores: List<NycSchoolSatScoresEntity>)
}