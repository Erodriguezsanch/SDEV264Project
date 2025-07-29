package com.example.jiujitsulog.data

import androidx.compose.runtime.mutableStateListOf

object SessionRepository {
    private val sessions = mutableStateListOf<Session>()

    fun addSession(session: Session) {
        sessions.add(session)
    }

    fun getSessions(): List<Session> = sessions

    fun removeSession(index: Int) {
        if (index in sessions.indices) {
            sessions.removeAt(index)
        }
    }
}