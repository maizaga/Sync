package com.maizaga.sync.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maizaga.sync.data.PersonJson

/**
 *
 * Created by maizaga on 2019-08-24.
 */
@Entity data class Person (
    @PrimaryKey
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "name") val name: String
) {
    override fun toString(): String = "id: $id, name: $name"

    companion object {
        fun fromJson(json: PersonJson) = with(json) { Person(id, name) }
    }
}