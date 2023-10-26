package com.example.assignmentone.di.module

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.example.assignmentone.di.factory.CustomFragmentFactory
import com.example.assignmentone.di.key.FragmentKey
import com.example.assignmentone.ui.fragments.CharacterDetailsFragment
import com.example.assignmentone.ui.fragments.DownloaderFragment
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(
    includes = [
        AdapterModule::class
    ]
)
abstract class FramentBindingModule {

    @Binds
    @IntoMap
    @FragmentKey(DownloaderFragment::class)
    abstract fun bindCharacterFragment(characterFragment: DownloaderFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(CharacterDetailsFragment::class)
    abstract fun bindCharacterDetailsFragment(characterDetailsFragment: CharacterDetailsFragment): Fragment

    @Binds
    abstract fun bindFragmentFactory(factory: CustomFragmentFactory) : FragmentFactory
}