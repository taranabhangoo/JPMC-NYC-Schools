package com.jpmc.nycschools.ui.schooldetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.jpmc.nycschools.R
import com.jpmc.nycschools.common.BaseBindingFragment
import com.jpmc.nycschools.data.repository.DefaultNycSchoolRepository
import com.jpmc.nycschools.data.source.local.NycSchoolsDatabase
import com.jpmc.nycschools.data.source.local.RoomLocalDataSource
import com.jpmc.nycschools.data.source.remote.RetrofitRemoteDataSource
import com.jpmc.nycschools.databinding.FragmentSchoolDetailsBinding
import com.jpmc.nycschools.ui.viewmodel.MainViewModel
import com.jpmc.nycschools.ui.viewmodel.ViewModelFactory


const val ARG_SCHOOL_ID = "ARG_SCHOOL_ID"

class NycSchoolDetailsFragment : BaseBindingFragment<FragmentSchoolDetailsBinding>() {
    private var schoolId: String? = null

    private lateinit var viewModel: NycSchoolDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            schoolId = it.getString(ARG_SCHOOL_ID)
        }

        // instantiate repository and view model
        val repository = DefaultNycSchoolRepository(
            RoomLocalDataSource(
                NycSchoolsDatabase.getInstance(requireContext().applicationContext).nycSchoolsDao,
                NycSchoolsDatabase.getInstance(requireContext().applicationContext).nycSchoolsSatScoresDao),
            RetrofitRemoteDataSource()
        )
        val viewModelProvider = activity?.let { ViewModelProvider(it, ViewModelFactory<MainViewModel>(repository)) }
        viewModel = viewModelProvider?.get(NycSchoolDetailsViewModel::class.java)!!
    }

    private fun registerObservers() {
        viewModel.updateSchoolDetails.observe(viewLifecycleOwner) {
            // update the details
            requireBinding().apply {
                this.school = it.school
                this.satScore = it.schoolSatScores
            }
        }
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSchoolDetailsBinding {
        return FragmentSchoolDetailsBinding.inflate(
            inflater,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerObservers()
        schoolId?.let { viewModel.getSchoolDetails(it) }
    }

    companion object {
        const val TAG: String = "NycSchoolDetailsFragment"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param schoolId The id of the school whose details are to be shown
         * @return A new instance of fragment NycSchoolDetails.
         */
        @JvmStatic
        fun newInstance(schoolId: String) =
            NycSchoolDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_SCHOOL_ID, schoolId)
                }
            }
    }
}