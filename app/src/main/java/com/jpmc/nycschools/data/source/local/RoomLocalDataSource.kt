package com.jpmc.nycschools.data.source.local

class RoomLocalDataSource(
    private val nycSchoolsDao: NycSchoolsDao,
    private val nycSchoolsSatScoresDao: NycSchoolsSatScoresDao
): LocalDataSource {
    override fun getSchools(): List<NycSchoolEntity> {
        return nycSchoolsDao.getSchools()
    }

    override fun getSchool(id: String): NycSchoolEntity {
        return nycSchoolsDao.getSchoolById(id)
    }

    override fun getSchoolSatScores(id: String): NycSchoolSatScoresEntity? {
        return nycSchoolsSatScoresDao.getSchoolSatScoresBySchoolId(id)
    }

    override fun addSchools(schools: List<NycSchoolEntity>) {
        nycSchoolsDao.insertAll(schools)
    }

    override fun addSchoolSatScores(schoolSatScores: List<NycSchoolSatScoresEntity>) {
        nycSchoolsSatScoresDao.insertAll(schoolSatScores)
    }
}