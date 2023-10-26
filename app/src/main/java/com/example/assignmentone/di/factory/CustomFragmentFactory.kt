package com.example.assignmentone.di.factory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider

class CustomFragmentFactory @Inject constructor(
    private val creators: Map<Class<out Fragment>, @JvmSuppressWildcards Provider<Fragment>>
): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        val fragmentClass = loadFragmentClass(classLoader, className)
        val creator = creators[fragmentClass] ?: return createFragment(classLoader, className)

        try {
            return creator.get()
        } catch (e: Exception) {
            Timber.e(e)
            throw RuntimeException(e)
        }
    }

    private fun createFragment(classLoader: ClassLoader, className: String): Fragment {
        return super.instantiate(classLoader, className)
    }
}