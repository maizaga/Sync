package com.maizaga.sync.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maizaga.sync.data.SessionJson
import java.util.*

/**
 *
 * Created by maizaga on 2019-08-24.
 */
@Entity data class Session(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "start") val start: Date,
    @ColumnInfo(name = "end") val end: Date
) {
    override fun equals(other: Any?): Boolean {
        return (other is Session) && id == other.id && name == other.name &&
                description == other.description && start == other.start && end == other.end
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun toString(): String = "id: $id, name: $name, description: $description, start: " +
            "$start, end: $end"

    companion object {
        fun fromJson(json: SessionJson) = with(json) {
            Session(id, name, description, Date(start * 1000), Date(end * 1000))
        }
    }
}