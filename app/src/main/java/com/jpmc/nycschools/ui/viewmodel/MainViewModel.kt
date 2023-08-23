package com.jpmc.nycschools.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jpmc.nycschools.data.repository.NycSchool
import com.jpmc.nycschools.data.repository.NycSchoolRepository
import com.jpmc.nycschools.ui.schoolslist.ApiStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel(
    private val repository: NycSchoolRepository
): ViewModel() {

    private val _updateNycSchools = MutableLiveData<List<NycSchool>>()
    val updateNycSchool = _updateNycSchools

    private val _schoolClickedEvent = MutableLiveData<String>()
    val schoolClickedEvent: LiveData<String>
        get() = _schoolClickedEvent

    private val _apiStatus = MutableLiveData<ApiStatus>()
    val apiStatus: LiveData<ApiStatus>
        get() = _apiStatus

    fun onViewCreated() {
        _apiStatus.postValue(ApiStatus.LOADING)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val schoolsList = repository.getSchools()
                _updateNycSchools.postValue(schoolsList)
                _apiStatus.postValue(ApiStatus.DONE)
            } catch (exception: Exception) {
                // To have a better solution we can have an way to handle fails
                // by trying to get one more time or
                // calling the refreshAPI and then getting data
                _apiStatus.postValue(ApiStatus.ERROR)
                println("Exception: ${exception.stackTrace}")
            }
        }
    }

    fun onSchoolClicked(schoolId: String) {
        _schoolClickedEvent.postValue(schoolId)
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.refreshNycSchoolsList()
            } catch (exception: Exception) {
                // In production, this can be handled in a better way,
                // by catching individual exceptions - like IOException
                // And we can send this data to our analytics tool
                println("Exception: ${exception.stackTrace}")
            }
        }
    }

}