package com.jpmc.nycschools.ui.schooldetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jpmc.nycschools.data.repository.NycSchoolDetails
import com.jpmc.nycschools.data.repository.NycSchoolRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NycSchoolDetailsViewModel(
    val repository: NycSchoolRepository
): ViewModel() {

    private val _updateSchoolDetails = MutableLiveData<NycSchoolDetails>()
    val updateSchoolDetails: LiveData<NycSchoolDetails> = _updateSchoolDetails


    fun getSchoolDetails(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val nycSchool = repository.getSchool(id)
            val nycSchoolSatScores = repository.getSchoolSatScores(id)
            withContext(Dispatchers.Main) {
                _updateSchoolDetails.postValue(
                    NycSchoolDetails(
                        nycSchool,
                        nycSchoolSatScores
                    )
                )
            }
        }
    }
}

