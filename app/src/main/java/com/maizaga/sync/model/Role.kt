package com.maizaga.sync.model

/**
 *
 * Created by maizaga on 2019-08-24.
 */
enum class Role(val description: String) {
    SPEAKERS("speakers"),
    SPONSORS("sponsors"),
    MODERATORS("moderators"),
    ARTISTS("artists"),
    EXHIBITORS("exhibitors");

    companion object {
        fun role(description: String) = values().find { role -> role.description == description }
    }
}