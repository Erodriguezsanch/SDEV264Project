package com.example.jiujitsulog.data

import androidx.compose.runtime.mutableStateListOf
import java.text.SimpleDateFormat
import java.util.*
import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object SessionRepository {
    private val sessions = mutableStateListOf<Session>()
    private lateinit var appContext: Context
    private val gson = Gson()
    private val prefsKey = "saved_sessions"

    fun init(context: Context) {
        appContext = context.applicationContext
        loadSessions()
    }

    fun addSession(session: Session) {
        sessions.add(session)
        saveSessions()
    }

    fun getSessions(): List<Session> = sessions

    fun removeSession(index: Int) {
        if (index in sessions.indices) {
            sessions.removeAt(index)
            saveSessions()
        }
    }

    private fun saveSessions() {
        val json = gson.toJson(sessions)
        appContext.getSharedPreferences("session_prefs", Context.MODE_PRIVATE)
            .edit().putString(prefsKey, json).apply()
    }

    private fun loadSessions() {
        val json = appContext.getSharedPreferences("session_prefs", Context.MODE_PRIVATE)
            .getString(prefsKey, null)

        if (!json.isNullOrEmpty()) {
            val type = object : TypeToken<List<Session>>() {}.type
            val loadedSessions: List<Session> = gson.fromJson(json, type)
            sessions.clear()
            sessions.addAll(loadedSessions)
        }
    }
}