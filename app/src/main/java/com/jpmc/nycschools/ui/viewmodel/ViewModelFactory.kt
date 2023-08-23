package com.jpmc.nycschools.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.jpmc.nycschools.data.repository.NycSchoolRepository
import com.jpmc.nycschools.ui.schooldetails.NycSchoolDetailsViewModel

/*class ViewModelFactory<T: ViewModel>(private val viewModel: Lazy<T>) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T =
        viewModel.get() as T

    *//**
     * Returns an instance of a defined ViewModel class.
     *//*
    inline fun <reified R: T> get(viewModelStoreOwner: ViewModelStoreOwner): T {
        return ViewModelProvider(viewModelStoreOwner, this)[R::class.java]
    }
}*/

class ViewModelFactory<T : ViewModel>(
    private val repository: NycSchoolRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return with(modelClass) {
            when {
                isAssignableFrom(MainViewModel::class.java) -> MainViewModel(repository)
                isAssignableFrom(NycSchoolDetailsViewModel::class.java) -> NycSchoolDetailsViewModel(repository)
                else -> throw IllegalArgumentException("Unknown ViewModel class")
            }
        } as T
    }
}
