package com.example.assignmentone.navigation

import android.content.Context
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.example.assignmentone.di.factory.CustomFragmentFactory
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class CustomNavHost: NavHostFragment() {

    @Inject
    lateinit var factory: CustomFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        childFragmentManager.fragmentFactory = factory
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}