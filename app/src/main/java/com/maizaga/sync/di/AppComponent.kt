package com.maizaga.sync.di

import android.app.Application
import com.maizaga.sync.SyncApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 *
 * Created by maizaga on 2019-08-24.
 */
@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ActivityBuildersModule::class])
interface AppComponent {
    fun inject(app: SyncApplication)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}