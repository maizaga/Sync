package com.maizaga.sync.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.maizaga.sync.model.Person
import com.maizaga.sync.model.Session
import com.maizaga.sync.model.SessionWithPeople

/**
 *
 * Created by maizaga on 2019-08-24.
 */
@Database(entities = [Session::class, Person::class, SessionWithPeople::class], version = 1, exportSchema = false)
@TypeConverters(TypesConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun sessionDao(): SessionDao
}