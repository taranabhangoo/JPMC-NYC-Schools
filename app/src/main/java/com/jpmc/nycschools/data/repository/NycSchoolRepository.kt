package com.jpmc.nycschools.data.repository

interface NycSchoolRepository {
    suspend fun getSchools(): List<NycSchool>
    suspend fun getSchool(id: String): NycSchool
    suspend fun getSchoolSatScores(id: String): NycSchoolSatScores?
    suspend fun refreshNycSchoolsList()
}