package com.jpmc.nycschools.data.source.remote

import com.jpmc.nycschools.data.source.local.NycSchoolEntity
import com.jpmc.nycschools.data.source.local.NycSchoolSatScoresEntity

interface RemoteDataSource {
    suspend fun getSchools(): List<NycSchoolEntity>
    suspend fun getSchoolSatScores(): List<NycSchoolSatScoresEntity>
}