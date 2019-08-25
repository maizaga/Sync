package com.maizaga.sync.di

import com.maizaga.sync.activities.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 *
 * Created by maizaga on 2019-08-24.
 */
@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity
}