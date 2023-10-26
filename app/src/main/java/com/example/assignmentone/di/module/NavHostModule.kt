package com.example.assignmentone.di.module

import com.example.assignmentone.navigation.CustomNavHost
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class NavHostModule {
    @ContributesAndroidInjector(
        modules = [
            FramentBindingModule::class
        ]
    )
    abstract fun contributeNavHost(): CustomNavHost
}