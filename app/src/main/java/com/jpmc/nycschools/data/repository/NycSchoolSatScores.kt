package com.jpmc.nycschools.data.repository

data class NycSchoolSatScores (
    val schoolId: String,
    val schoolName: String,
    val numOfSatTestTakers: String,
    val satCriticalReadingAvgScore: String,
    val satMathAvgScore: String,
    val satWritingAvgScore: String,
    )