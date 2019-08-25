package com.maizaga.sync.data

import android.content.Context
import androidx.annotation.RawRes
import com.google.gson.Gson
import com.maizaga.sync.di.SchedulerProviders
import io.reactivex.Observable

/**
 *
 * Created by maizaga on 2019-08-24.
 */
class JsonRepository(private val context: Context, private val gson: Gson,
                     private val schedulerProviders: SchedulerProviders) {

    fun readJson(@RawRes jsonResource: Int): Observable<SessionJsonReader> =
        Observable.fromCallable {
            context.resources.openRawResource(jsonResource).bufferedReader().use {
                return@fromCallable it.readText()
            }
        }
            .flatMap { text -> Observable.just(gson.fromJson(text, SessionJsonReader::class.java)) }
            .subscribeOn(schedulerProviders.io())
            .observeOn(schedulerProviders.computation())
}