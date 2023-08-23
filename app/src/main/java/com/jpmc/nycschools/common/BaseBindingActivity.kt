package com.jpmc.nycschools.common

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseBindingActivity<BINDING: ViewBinding>: AppCompatActivity() {

    protected val binding: BINDING by lazy { inflateBinding(layoutInflater) }

    protected abstract fun inflateBinding(inflater: LayoutInflater): BINDING

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}