package com.maizaga.sync.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.maizaga.sync.data.AppDatabase
import com.maizaga.sync.data.SessionDao
import com.maizaga.sync.data.JsonRepository
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

/**
 *
 * Created by maizaga on 2019-08-24.
 */
@Module(includes = [ViewModelModule::class])
class AppModule {
    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Singleton
    @Provides
    fun provideDb(app: Application): AppDatabase = Room.databaseBuilder(app,
        AppDatabase::class.java, "sync.db").build()

    @Singleton
    @Provides
    fun provideSessionDao(db: AppDatabase): SessionDao = db.sessionDao()

    @Singleton
    @Provides
    fun provideSchedulers() = object : SchedulerProviders {
        override fun ui(): Scheduler = AndroidSchedulers.mainThread()
        override fun io(): Scheduler = Schedulers.io()
        override fun computation() = Schedulers.computation()
    }

    @Singleton
    @Provides
    fun provideRepository(app: Application, gson: Gson, schedulerProviders: SchedulerProviders)
            : JsonRepository = JsonRepository(app, gson, schedulerProviders)
}