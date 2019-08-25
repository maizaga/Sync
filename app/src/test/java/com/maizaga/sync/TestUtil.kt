package com.maizaga.sync

import com.maizaga.sync.model.Role
import com.maizaga.sync.model.Session
import com.maizaga.sync.model.SessionWithPeople
import java.util.*

/**
 *
 * Created by maizaga on 2019-08-25.
 */
object TestUtil {

    fun initialText() = "{\"token_expiry\": null,\"result\": [{\"id\": \"c07aed75de7cb71af7374d44825f1112\",\"name\": \"Registration\",\"artists\": [{\"id\": \"6996364\",\"name\": \"Taylor McKnight\"}],\"start\": 1571497200,\"end\": 1571500800}, {\"id\": \"cd87ef377b2a8c95493b2e561381566a\",\"name\": \"Spacewalk Simulator\",\"description\": \"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\",\"speakers\": [{\"id\": \"3677032\",\"name\": \"Neil Armstrong\"}, {\"id\": \"6996370\",\"name\": \"Peggy Witson\"}],\"sponsors\": [{\"id\": \"6996373\",\"name\": \"NASA\"}],\"moderators\": [{\"id\": \"6996400\",\"name\": \"Yuri Gagarin\"}, {\"id\": \"4241674\",\"name\": \"George Jetson\"}],\"start\": 1571500800,\"end\": 1571508000}]}"

    fun initialSessions() = arrayListOf(Session("c07aed75de7cb71af7374d44825f1112", "Registration", null, Date(1571497200000), Date(1571500800000)), Session("cd87ef377b2a8c95493b2e561381566a", "Spacewalk Simulator", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", Date(1571500800000), Date(1571508000000)))

    fun rolesForSessions() = arrayListOf(SessionWithPeople("c07aed75de7cb71af7374d44825f1112", "6996364", Role.ARTISTS.description), SessionWithPeople("cd87ef377b2a8c95493b2e561381566a", "3677032", Role.SPEAKERS.description), SessionWithPeople("cd87ef377b2a8c95493b2e561381566a", "6996370", Role.SPEAKERS.description), SessionWithPeople("cd87ef377b2a8c95493b2e561381566a", "6996373", Role.SPONSORS.description), SessionWithPeople("cd87ef377b2a8c95493b2e561381566a", "6996400", Role.MODERATORS.description), SessionWithPeople("cd87ef377b2a8c95493b2e561381566a", "4241674", Role.MODERATORS.description))

    fun editedText() = "{\"token_expiry\": null,\"result\": [{\"id\": \"c07aed75de7cb71af7374d44825f1112\",\"name\": \"Registration\",\"artists\": [{\"id\": \"6996364\",\"name\": \"Taylor McKnight\"}],\"start\": 1571497200,\"end\": 1571500800}, {\"id\": \"cd87ef377b2a8c95493b2e561381566a\",\"name\": \"Spacewalk Simulator\",\"description\": \"Much Shorter, to trigger update\",\"speakers\": [{\"id\": \"3677032\",\"name\": \"Neil Armstrong\"}, {\"id\": \"6996370\",\"name\": \"Peggy Witson\"}],\"sponsors\": [{\"id\": \"6996373\",\"name\": \"NASA\"}],\"moderators\": [{\"id\": \"6996400\",\"name\": \"Yuri Gagarin\"}, {\"id\": \"4241674\",\"name\": \"George Jetson\"}],\"start\": 1571500800,\"end\": 1571508000}]}"

    fun editedSessions() = arrayListOf(Session("c07aed75de7cb71af7374d44825f1112", "Registration", null, Date(1571497200000), Date(1571500800000)), Session("cd87ef377b2a8c95493b2e561381566a", "Spacewalk Simulator", "Much Shorter, to trigger update", Date(1571500800000), Date(1571508000000)))

    fun rolesForEditedSessions() = arrayListOf(SessionWithPeople("c07aed75de7cb71af7374d44825f1112", "6996364", Role.ARTISTS.description), SessionWithPeople("cd87ef377b2a8c95493b2e561381566a", "3677032", Role.SPEAKERS.description), SessionWithPeople("cd87ef377b2a8c95493b2e561381566a", "6996370", Role.SPEAKERS.description), SessionWithPeople("cd87ef377b2a8c95493b2e561381566a", "6996373", Role.SPONSORS.description), SessionWithPeople("cd87ef377b2a8c95493b2e561381566a", "6996400", Role.MODERATORS.description), SessionWithPeople("cd87ef377b2a8c95493b2e561381566a", "4241674", Role.MODERATORS.description))

    fun removeText() = "{\"token_expiry\": null,\"result\": [{\"id\": \"c07aed75de7cb71af7374d44825f1112\",\"name\": \"Registration\",\"start\": 1571497200,\"end\": 1571500800}]}"

    fun deletedSessions() = arrayListOf(Session("c07aed75de7cb71af7374d44825f1112", "Registration", null, Date(1571497200000), Date(1571500800000)))

    fun rolesForDeletedSessions() = arrayListOf<SessionWithPeople>()
}