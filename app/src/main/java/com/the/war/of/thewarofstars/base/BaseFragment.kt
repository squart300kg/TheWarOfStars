package com.the.war.of.thewarofstars.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

open class BaseFragment<T: ViewDataBinding>(layoutRes: Int) : Fragment() {

    protected lateinit var binding: T

    private val layoutRes = layoutRes

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        return binding.root
    }

    protected fun binding(action: T.() -> Unit) {
        binding.run(action)
    }
}