package com.maizaga.sync.data

import androidx.room.TypeConverter
import java.util.*

/**
 *
 * Created by maizaga on 2019-08-24.
 */
class TypesConverter {

    @TypeConverter
    fun fromDate(date: Date?): Long? = date?.time

    @TypeConverter
    fun toDate(time: Long?): Date? = if (time == null) null else Date(time)
}