package com.jpmc.nycschools.data.repository

import com.jpmc.nycschools.data.source.local.LocalDataSource
import com.jpmc.nycschools.data.source.remote.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultNycSchoolRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : NycSchoolRepository {
    override suspend fun getSchools(): List<NycSchool> {
        // get data from local database first but if it fails, fetch from api
        val schools = localDataSource.getSchools().map {
            it.toNycSchool()
        }
        return schools.ifEmpty {
            remoteDataSource.getSchools().map { it.toNycSchool() } }
    }

    override suspend fun getSchool(id: String): NycSchool {
        return localDataSource.getSchool(id).toNycSchool()
    }

    override suspend fun getSchoolSatScores(id: String): NycSchoolSatScores? {
        return localDataSource.getSchoolSatScores(id)?.toNycSchoolSatScores()
    }

    override suspend fun refreshNycSchoolsList() {
        withContext(Dispatchers.IO) {
            val schools = remoteDataSource.getSchools()
            val schoolsSatScores = remoteDataSource.getSchoolSatScores()
            localDataSource.addSchools(schools)
            localDataSource.addSchoolSatScores(schoolsSatScores)
        }
    }
}