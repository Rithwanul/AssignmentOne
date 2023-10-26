package com.example.assignmentone.base

import com.example.assignmentone.BuildConfig
import com.example.assignmentone.di.component.DaggerAppComponent
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

class BaseApplication(): DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = DaggerAppComponent.factory().create(this)

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {

            val formatStrategy: FormatStrategy = PrettyFormatStrategy
                .newBuilder().showThreadInfo(true)
                .methodCount(1)
                .methodOffset(1)
                .build()

            Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))

            Timber.plant(object : Timber.DebugTree() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    Logger.log(priority, tag, message, t)
                }
            })
        }
    }

}