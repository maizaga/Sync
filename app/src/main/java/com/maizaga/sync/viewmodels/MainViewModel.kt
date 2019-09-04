package com.maizaga.sync.viewmodels

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.maizaga.sync.adapters.MainAdapter
import com.maizaga.sync.data.JsonRepository
import com.maizaga.sync.data.PersonJson
import com.maizaga.sync.data.SessionDao
import com.maizaga.sync.data.SessionJsonReader
import com.maizaga.sync.di.SchedulerProviders
import com.maizaga.sync.model.Person
import com.maizaga.sync.model.Role
import com.maizaga.sync.model.Session
import com.maizaga.sync.model.SessionWithPeople
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 *
 * Created by maizaga on 2019-08-24.
 */
class MainViewModel @Inject constructor(
    private val jsonRepository: JsonRepository,
    private val sessionDao: SessionDao,
    private val schedulerProviders: SchedulerProviders): ViewModel() {
    private val tag = this::class.java.name

    private val disposables = CompositeDisposable()

    val updated = ObservableField<String>("0 Updated")
    val removed = ObservableField<String>("0 Removed")
    val inserted = ObservableField<String>("0 Inserted")
    val progress = ObservableBoolean(false)

    lateinit var adapter: MainAdapter

    fun importJson() {
        disposables.add(jsonRepository.readJson(adapter.selectedJsonResource)
            .doOnSubscribe { progress.set(true) }
            .subscribe({ sessionJsonReader: SessionJsonReader? ->
                // Computation thread
                sessionJsonReader?.let { startSync(it) }
            }, { t: Throwable ->  // onError
                progress.set(false)
                Log.e(tag, "Something went wrong: ${t.printStackTrace()}")
            }))
    }

    private fun startSync(sessionJsonReader: SessionJsonReader) {
        disposables.add(Observable.fromCallable { syncValues(sessionJsonReader) }
            .subscribeOn(schedulerProviders.computation())
            .observeOn(schedulerProviders.ui()).subscribe({ counters ->
                updated.set("${counters[UPDATED]} Updated")
                removed.set("${counters[REMOVED]} Removed")
                inserted.set("${counters[INSERTED]} Inserted")
                progress.set(false)
            }, { t ->
                progress.set(false)
                Log.e(tag, "Something went wrong: ${t.printStackTrace()}")
            }))
    }

    private fun syncValues(sessionJsonReader: SessionJsonReader): HashMap<String, Int> {
        val counters = hashMapOf(
            UPDATED to mutableSetOf<String>(),
            REMOVED to mutableSetOf(),
            INSERTED to mutableSetOf())
        val jsonSessionIds = hashMapOf<String, List<SessionWithPeople>>()
        sessionJsonReader.result.forEach { sessionJson ->
            // Insert all new Sessions, Insert all new People, Insert all new relations:
            val session = Session.fromJson(sessionJson)
            val storedSession = sessionDao.getSessionsById(session.id)
            storedSession?.let {
                if (session != it) counters[UPDATED]?.add(session.id)
            } ?: run { counters[INSERTED]?.add(session.id) }
            sessionDao.insertSession(session)
            val speakers = sessionJson.speakers?.let { insertPeople(session, it, Role.SPEAKERS) }
            val sponsors = sessionJson.sponsors?.let { insertPeople(session, it, Role.SPONSORS) }
            val moderators = sessionJson.moderators?.let { insertPeople(session, it,
                Role.MODERATORS) }
            val artists = sessionJson.artists?.let { insertPeople(session, it, Role.ARTISTS) }
            val exhibitors = sessionJson.exhibitors?.let { insertPeople(session, it,
                Role.EXHIBITORS) }

            jsonSessionIds[session.id] = (speakers ?: arrayListOf()) + (sponsors ?: arrayListOf()) +
                    (moderators ?: arrayListOf()) + (artists ?: arrayListOf()) +
                    (exhibitors ?: arrayListOf())
        }

        // Remove sessions that are no longer required among those roles
        val storedSessionIdsNotInJson = sessionDao.getSessions().filterNot { storedSession ->
            jsonSessionIds.keys.contains(storedSession.id)
        }.map { session -> session.id }
        sessionDao.removeRelations(storedSessionIdsNotInJson)
        sessionDao.removeSessions(storedSessionIdsNotInJson)
        counters[REMOVED] = storedSessionIdsNotInJson.toMutableSet()

        // Now for those sessions that no longer have specific roles:
        for ((sessionId, relations) in jsonSessionIds) {
            val relationsToRemove = sessionDao.getRolesForSession(sessionId).filterNot {
                    storedRelation -> relations.contains(storedRelation)
            }
            if (relationsToRemove.isNotEmpty()) counters[UPDATED]?.add(sessionId)
            relationsToRemove.forEach { sessionDao.removeRelation(it) }
        }

        // Finally, remove users that don't belong to any session
        sessionDao.removePeopleWithNoSession()

        return hashMapOf(
            UPDATED to (counters[UPDATED]?.size ?: 0),
            REMOVED to (counters[REMOVED]?.size ?: 0),
            INSERTED to (counters[INSERTED]?.size ?: 0))
    }

    private fun insertPeople(session: Session, people: List<PersonJson>, role: Role):
            List<SessionWithPeople> {
        val relations = arrayListOf<SessionWithPeople>()
        people.forEach { personJson ->
            sessionDao.insertPerson(Person.fromJson(personJson))
            val relation = SessionWithPeople.from(session, Person.fromJson(personJson), role)
            sessionDao.insertSessionWithPeople(relation)
            relations.add(relation)
        }

        return relations
    }

    fun dbDump() {
        progress.set(true)
        disposables.add(Observable.fromCallable {
            var output = "DB Dump\n"
            sessionDao.getSessions().forEach { session ->
                output += "Session: $session\n"
                sessionDao.getRolesForSession(session.id).forEach { relation ->
                    output += "${relation.role}: ${sessionDao.getPersonById(relation.personId)}\n"
                }
            }
            sessionDao.getPeople().forEach { person ->
                output += "Person: $person"
            }
            return@fromCallable output
        }
            .subscribeOn(schedulerProviders.io())
            .observeOn(schedulerProviders.ui()).subscribe({ output ->
                Log.d(tag, output)
                progress.set(false)
            }, { t ->
                Log.e(tag, "Something went wrong: ${t.printStackTrace()}")
            }))
    }

    override fun onCleared() {
        super.onCleared()

        disposables.dispose()
    }

    companion object {
        private const val UPDATED = "updated"
        private const val REMOVED = "removed"
        private const val INSERTED = "inserted"
    }
}