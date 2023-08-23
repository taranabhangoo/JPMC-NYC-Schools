package com.jpmc.nycschools

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.jpmc.nycschools.common.BaseBindingActivity
import com.jpmc.nycschools.data.repository.DefaultNycSchoolRepository
import com.jpmc.nycschools.data.source.local.NycSchoolsDatabase
import com.jpmc.nycschools.data.source.local.RoomLocalDataSource
import com.jpmc.nycschools.data.source.remote.RetrofitRemoteDataSource
import com.jpmc.nycschools.databinding.ActivityMainBinding
import com.jpmc.nycschools.ui.schooldetails.NycSchoolDetailsFragment
import com.jpmc.nycschools.ui.schoolslist.NycSchoolsListFragment
import com.jpmc.nycschools.ui.viewmodel.MainViewModel
import com.jpmc.nycschools.ui.viewmodel.ViewModelFactory


class MainActivity : BaseBindingActivity<ActivityMainBinding>() {

    private lateinit var viewModel: MainViewModel

    override fun inflateBinding(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set action bar
        setSupportActionBar(binding.toolbar)

        val repository = DefaultNycSchoolRepository(
            RoomLocalDataSource(
                NycSchoolsDatabase.getInstance(this.applicationContext).nycSchoolsDao,
                NycSchoolsDatabase.getInstance(this.applicationContext).nycSchoolsSatScoresDao
            ),
            RetrofitRemoteDataSource()
        )
        val viewModelProvider = ViewModelProvider(this, ViewModelFactory<MainViewModel>(repository))
        viewModel = viewModelProvider.get(MainViewModel::class.java)

        val fragment = NycSchoolsListFragment()
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(
                binding.fragmentContainerView.id,
                fragment,
                NycSchoolsListFragment.TAG
            )
        }

        registerObservers()
    }

    private fun registerObservers() {
        viewModel.schoolClickedEvent.observe(this@MainActivity) {
            openDetailsFragment(it)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == R.id.home) {
            // Handle Up button click (navigate up or perform an action)
            onBackPressed() // This simulates the default back button behavior
            // Remove the up button
            // Generally, we should check based on what fragment is shown,
            // but for just 2 fragments, it will work like this
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openDetailsFragment(schoolId: String) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            val fragment = NycSchoolDetailsFragment.newInstance(schoolId)
            replace(
                binding.fragmentContainerView.id,
                fragment,
                NycSchoolDetailsFragment.TAG
            )
            addToBackStack(NycSchoolsListFragment.TAG)
        }
        // Set the up button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}