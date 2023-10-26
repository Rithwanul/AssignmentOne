package com.example.assignmentone.di.module

import com.example.assignmentone.ui.activitiy.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(
        modules = [
            NavHostModule::class
        ]
    )
    abstract fun contributeMainActivity(): MainActivity
}