package com.maizaga.sync.model

import androidx.room.Entity
import androidx.room.Index

/**
 *
 * Created by maizaga on 2019-08-24.
 */
@Entity(indices = [Index("sessionId")], primaryKeys = ["sessionId", "personId", "role"])
data class SessionWithPeople(
    val sessionId: String,
    val personId: String,
    val role: String
) {
    override fun equals(other: Any?): Boolean {
        return (other is SessionWithPeople) && sessionId == other.sessionId
                && personId == other.personId && role == other.role
    }

    override fun toString(): String = "sessionId: $sessionId, personId: $personId, role: $role"

    companion object {
        fun from(session: Session, person: Person, role: Role) =
            SessionWithPeople(session.id, person.id, role.description)
    }
}