package com.jpmc.nycschools.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseBindingFragment<BINDING : ViewBinding> : Fragment() {

    protected var binding: BINDING? = null
        private set

    protected abstract fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): BINDING

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflateBinding(inflater, container).also { binding = it }.root

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    protected fun requireBinding(): BINDING =
        checkNotNull(binding) { "Either the ViewBinding has not been initialized OR it has already been cleared." }

}