package com.maizaga.sync.viewmodels

import com.google.gson.Gson
import com.maizaga.sync.R
import com.maizaga.sync.TestUtil
import com.maizaga.sync.data.JsonRepository
import com.maizaga.sync.data.SessionDao
import com.maizaga.sync.data.SessionJsonReader
import com.maizaga.sync.di.SchedulerProviders
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 *
 * Created by maizaga on 2019-08-25.
 */
@RunWith(JUnit4::class)
class MainViewModelTest {

    @Mock lateinit var jsonRepositoryMock: JsonRepository
    @Mock lateinit var sessionDaoMock: SessionDao
    @Mock lateinit var schedulerProviders: SchedulerProviders

    lateinit var viewModel: MainViewModel

    private val initialSessions = TestUtil.initialSessions()
    private val rolesForSessions = TestUtil.rolesForSessions()
    private val rolesForSessionOne = rolesForSessions.filter { relation -> relation.sessionId == initialSessions[0].id }
    private val rolesForSessionTwo = rolesForSessions.filter { relation -> relation.sessionId == initialSessions[1].id }

    private val editedSessions = TestUtil.editedSessions()
    private val rolesForEditedSessions = TestUtil.rolesForEditedSessions()
    private val rolesForEditedSessionOne = rolesForEditedSessions.filter { relation -> relation.sessionId == editedSessions[0].id }
    private val rolesForEditedSessionTwo = rolesForEditedSessions.filter { relation -> relation.sessionId == editedSessions[1].id }

    private val deletedSessions = TestUtil.deletedSessions()
    private val rolesForDeletedSessions = TestUtil.rolesForDeletedSessions()
    private val rolesForDeletedSessionOne = rolesForDeletedSessions.filter { relation -> relation.sessionId == deletedSessions[0].id }

    private fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }

    private fun <T> uninitialized(): T = null as T

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = MainViewModel(jsonRepositoryMock, sessionDaoMock, schedulerProviders)

        Mockito.`when`(jsonRepositoryMock.readJson(R.raw.sessions_initial)).thenAnswer {
            Observable.just(Gson().fromJson(TestUtil.initialText(), SessionJsonReader::class.java))
        }

        Mockito.`when`(jsonRepositoryMock.readJson(R.raw.sessions_edited)).thenAnswer {
            Observable.just(Gson().fromJson(TestUtil.editedText(), SessionJsonReader::class.java))
        }

        Mockito.`when`(jsonRepositoryMock.readJson(R.raw.sessions_deleted)).thenAnswer {
            Observable.just(Gson().fromJson(TestUtil.removeText(), SessionJsonReader::class.java))
        }

        Mockito.`when`(schedulerProviders.io()).thenReturn(Schedulers.trampoline())
        Mockito.`when`(schedulerProviders.computation()).thenReturn(Schedulers.trampoline())
        Mockito.`when`(schedulerProviders.ui()).thenReturn(Schedulers.trampoline())
    }

    private fun runInitialTestValues() {
        Mockito.`when`(sessionDaoMock.getSessionsById(initialSessions[0].id)).thenReturn(null)
        Mockito.`when`(sessionDaoMock.getSessionsById(initialSessions[1].id)).thenReturn(null)
        Mockito.`when`(sessionDaoMock.getSessions()).thenAnswer { initialSessions }
        Mockito.`when`(sessionDaoMock.getRolesForSession(initialSessions[0].id)).thenAnswer { rolesForSessionOne }
        Mockito.`when`(sessionDaoMock.getRolesForSession(initialSessions[1].id)).thenAnswer { rolesForSessionTwo }

        viewModel.importJson()

        Mockito.verify(sessionDaoMock).insertSession(initialSessions[0])
        Mockito.verify(sessionDaoMock).insertSession(initialSessions[1])
        Mockito.verify(sessionDaoMock, Mockito.times(6)).insertPerson(any())
        for (record in rolesForSessions) {
            Mockito.verify(sessionDaoMock).insertSessionWithPeople(record)
        }

        Mockito.verify(sessionDaoMock).removeRelations(arrayListOf())
        Mockito.verify(sessionDaoMock).removeSessions(arrayListOf())

        Mockito.verify(sessionDaoMock).removePeopleWithNoSession()
    }

    @Test
    fun fetchInitialSessionAndLoadDatabase() {
        runInitialTestValues()

        assert(viewModel.updated.get() == "0 Updated")
        assert(viewModel.removed.get() == "0 Removed")
        assert(viewModel.inserted.get() == "2 Inserted")
        assert(!viewModel.progress.get())
    }

    @Test
    fun fetchEditedSessionAndLoadDatabase() {
        runInitialTestValues()

        viewModel.selectedJson = 1

        Mockito.`when`(sessionDaoMock.getSessionsById(editedSessions[0].id)).thenReturn(initialSessions[0])
        Mockito.`when`(sessionDaoMock.getSessionsById(editedSessions[1].id)).thenReturn(initialSessions[1])
        Mockito.`when`(sessionDaoMock.getSessions()).thenAnswer { initialSessions }
        Mockito.`when`(sessionDaoMock.getRolesForSession(editedSessions[0].id)).thenAnswer { rolesForEditedSessionOne }
        Mockito.`when`(sessionDaoMock.getRolesForSession(editedSessions[1].id)).thenAnswer { rolesForEditedSessionTwo }

        viewModel.importJson()

        assert(viewModel.updated.get() == "1 Updated")
        assert(viewModel.removed.get() == "0 Removed")
        assert(viewModel.inserted.get() == "0 Inserted")
        assert(!viewModel.progress.get())
    }

    @Test
    fun fetchDeletedSessionAndLoadDatabase() {
        runInitialTestValues()

        viewModel.selectedJson = 2

        Mockito.`when`(sessionDaoMock.getSessionsById(deletedSessions[0].id)).thenReturn(initialSessions[0])
        Mockito.`when`(sessionDaoMock.getSessions()).thenAnswer { initialSessions }
        Mockito.`when`(sessionDaoMock.getRolesForSession(deletedSessions[0].id)).thenAnswer { rolesForDeletedSessionOne }

        viewModel.importJson()

        assert(viewModel.updated.get() == "0 Updated")
        assert(viewModel.removed.get() == "1 Removed")
        assert(viewModel.inserted.get() == "0 Inserted")
        assert(!viewModel.progress.get())
    }
}