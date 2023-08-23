package com.jpmc.nycschools.ui.schoolslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.jpmc.nycschools.R
import com.jpmc.nycschools.common.BaseBindingFragment
import com.jpmc.nycschools.data.repository.DefaultNycSchoolRepository
import com.jpmc.nycschools.data.source.local.NycSchoolsDatabase
import com.jpmc.nycschools.data.source.local.RoomLocalDataSource
import com.jpmc.nycschools.data.source.remote.RetrofitRemoteDataSource
import com.jpmc.nycschools.databinding.FragmentNycSchoolsListBinding
import com.jpmc.nycschools.ui.viewmodel.MainViewModel
import com.jpmc.nycschools.ui.viewmodel.ViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class NycSchoolsListFragment : BaseBindingFragment<FragmentNycSchoolsListBinding>() {

    private val nycSchoolsAdapter = NycSchoolsListAdapter(
        ClickListener {
            activityViewModel.onSchoolClicked(it)
        }
    )

    private lateinit var activityViewModel: MainViewModel

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNycSchoolsListBinding {
        return FragmentNycSchoolsListBinding.inflate(inflater, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = DefaultNycSchoolRepository(
            RoomLocalDataSource(NycSchoolsDatabase.getInstance(requireContext().applicationContext).nycSchoolsDao,
                NycSchoolsDatabase.getInstance(requireContext().applicationContext).nycSchoolsSatScoresDao),
            RetrofitRemoteDataSource()
        )
        val viewModelProvider = activity?.let { ViewModelProvider(it, ViewModelFactory<MainViewModel>(repository)) }
        activityViewModel = viewModelProvider?.get(MainViewModel::class.java)!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireBinding().recyclerView.apply {
            // set up recycler view
            this.adapter = nycSchoolsAdapter
            this.addItemDecoration(VerticalItemDecoration(
                requireContext()
            ))
        }

        activityViewModel.onViewCreated()
        registerObservers()
    }

    private fun registerObservers() {
        activityViewModel.updateNycSchool.observe(viewLifecycleOwner) {
            // update the list of schools
            nycSchoolsAdapter.schools = it
        }
        activityViewModel.apiStatus.observe(viewLifecycleOwner) {
            // update the visibility of views
            updateViews(it)
        }
    }

    private fun updateViews(apiStatus: ApiStatus) {
        when (apiStatus) {
            ApiStatus.LOADING -> {
                // while trying to load data
                with(requireBinding()) {
                    apiStatusImageView.visibility = View.VISIBLE
                    apiStatusImageView.setImageResource(R.drawable.loading_animation)
                    recyclerView.visibility = View.GONE
                }
            }
            ApiStatus.ERROR -> {
                // when backend and local repo fails to return data
                with(requireBinding()) {
                    apiStatusImageView.visibility = View.VISIBLE
                    apiStatusImageView.setImageResource(R.drawable.ic_broken_image)
                    recyclerView.visibility = View.GONE
                }
            }
            ApiStatus.DONE -> {
                // when data is returned
                with(requireBinding()) {
                    apiStatusImageView.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                }
            }
        }
    }

    companion object {
        val TAG: String = NycSchoolsListFragment::class.java.name
    }
}