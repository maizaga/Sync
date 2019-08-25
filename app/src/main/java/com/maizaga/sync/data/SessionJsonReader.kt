package com.maizaga.sync.data

import com.google.gson.annotations.SerializedName
import java.util.Date

/**
 *
 * Created by maizaga on 2019-08-24.
 */
data class SessionJsonReader(@SerializedName("result") val result: List<SessionJson>)

data class SessionJson(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("start") val start: Long,
    @SerializedName("end") val end: Long,
    @SerializedName("speakers") val speakers: List<PersonJson>?,
    @SerializedName("sponsors") val sponsors: List<PersonJson>?,
    @SerializedName("moderators") val moderators: List<PersonJson>?,
    @SerializedName("artists") val artists: List<PersonJson>?,
    @SerializedName("exhibitors") val exhibitors: List<PersonJson>?
)

data class PersonJson(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String
)