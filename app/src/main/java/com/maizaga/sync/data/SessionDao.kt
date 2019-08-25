package com.maizaga.sync.data

import androidx.room.*
import com.maizaga.sync.model.Person
import com.maizaga.sync.model.Session
import com.maizaga.sync.model.SessionWithPeople

/**
 *
 * Created by maizaga on 2019-08-24.
 */
@Dao
interface SessionDao {

    @Query("SELECT * FROM Session")
    fun getSessions(): List<Session>

    @Query("SELECT * FROM Session WHERE id = :id")
    fun getSessionsById(id: String): Session?

    @Query("SELECT * FROM Person WHERE id = :id")
    fun getPersonById(id: String): Person?

    @Query("SELECT * FROM Person")
    fun getPeople(): List<Person>

    @Query("SELECT * FROM SessionWithPeople  WHERE sessionId = :sessionId")
    fun getRolesForSession(sessionId: String): List<SessionWithPeople>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSession(session: Session)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPerson(person: Person)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSessionWithPeople(sessionWithPeople: SessionWithPeople)

    @Query("DELETE FROM Session WHERE id IN (:ids)")
    fun removeSessions(ids: List<String>)

    @Query("DELETE FROM SessionWithPeople WHERE sessionId IN (:sessionIds)")
    fun removeRelations(sessionIds: List<String>)

    @Delete
    fun removeRelation(sessionWithPeople: SessionWithPeople)

    @Query("DELETE FROM Person WHERE id NOT IN (SELECT DISTINCT personId FROM SessionWithPeople)")
    fun removePeopleWithNoSession()
}