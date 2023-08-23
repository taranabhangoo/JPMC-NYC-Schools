package com.jpmc.nycschools.data.source.remote

import com.jpmc.nycschools.data.source.local.NycSchoolEntity
import com.jpmc.nycschools.data.source.local.NycSchoolSatScoresEntity

class RetrofitRemoteDataSource(
    private val retrofit: NycSchoolApiService = NycSchoolApi.retrofitService
): RemoteDataSource {

    override suspend fun getSchools(): List<NycSchoolEntity> {
        return retrofit.getSchools()

    }

    override suspend fun getSchoolSatScores(): List<NycSchoolSatScoresEntity> {
        return retrofit.getSatResults()
    }
}