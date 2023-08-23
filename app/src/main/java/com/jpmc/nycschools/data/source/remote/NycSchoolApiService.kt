package com.jpmc.nycschools.data.source.remote

import com.jpmc.nycschools.data.source.local.NycSchoolEntity
import com.jpmc.nycschools.data.source.local.NycSchoolSatScoresEntity
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://data.cityofnewyork.us"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface NycSchoolApiService {

    @GET(value = "/resource/s3k6-pzi2.json")
    suspend fun getSchools(): List<NycSchoolEntity>

    @GET(value = "/resource/f9bf-2cp4.json")
    suspend fun getSatResults(): List<NycSchoolSatScoresEntity>

}

object NycSchoolApi {
    val retrofitService: NycSchoolApiService by lazy {
        retrofit.create(NycSchoolApiService::class.java)
    }
}