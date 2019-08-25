package com.maizaga.sync.di

import io.reactivex.Scheduler

/**
 *
 * Created by maizaga on 2019-08-25.
 */
interface SchedulerProviders {
    fun ui(): Scheduler
    fun io(): Scheduler
    fun computation(): Scheduler
}